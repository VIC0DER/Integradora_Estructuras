package mx.utez.edu.SgPacientesApplication.component;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import mx.utez.edu.SgPacientesApplication.dtos.CreateUrgenciaDTO;
import mx.utez.edu.SgPacientesApplication.model.Cita;
import mx.utez.edu.SgPacientesApplication.model.Doctor;
import mx.utez.edu.SgPacientesApplication.model.Paciente;
import mx.utez.edu.SgPacientesApplication.model.Urgencia;
import mx.utez.edu.SgPacientesApplication.repository.CitaRepository;
import mx.utez.edu.SgPacientesApplication.repository.DoctorRepository;
import mx.utez.edu.SgPacientesApplication.repository.PacienteRepository;
import mx.utez.edu.SgPacientesApplication.repository.UrgenciaRepository;
import mx.utez.edu.SgPacientesApplication.structures.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

/**
    Esta clase se encarga de obtener información de la base de datos
    y actualizar en memoria en caso de inserción o eliminación
    para toda la aplicación.
 */
@Component
public class MemoryStore {
    /** Referencias a todos los repositorios. */
    private final PacienteRepository pacienteRepository;
    private final CitaRepository citaRepository;
    private final DoctorRepository doctorRepository;
    private final UrgenciaRepository urgenciaRepository;
    /** Lista para la persistencia de pacientes. */
    @Getter
    private final ListaSimple<Paciente> pacientes;
    /** Lista para la persistencia de citas. */
    @Getter
    private ListaSimple<Cita> citas;
    /** Lista para la persistencia de doctores. */
    @Getter
    private final ListaSimple<Doctor> doctores;
    /** Nos permite saber si el paciente ya fue previamente registrado. */
    @Getter
    private final MyHashMap<String, Paciente> mapPorCurp;
    /** Lista de doctores del departamento de urgencias. */
    @Getter
    private final ListaSimple<Doctor> doctoresUrgencias;
    /** Nos permite gestionar la atención a urgencias. */
    @Getter
    private final MinHeap urgenciasHeap;
    /** Nos permite ordenar las urgencias que ya han sido atendidas como pila.*/
    @Getter
    private final Pila<Urgencia> urgenciasAtendidas;
    @Getter
    private final Cola<Cita> citasActivas;

    public MemoryStore(
            PacienteRepository pacienteRepository,
            CitaRepository citaRepository,
            DoctorRepository doctorRepository,
            UrgenciaRepository urgenciaRepository) {
        this.pacienteRepository = pacienteRepository;
        this.citaRepository = citaRepository;
        this.doctorRepository = doctorRepository;
        this.urgenciaRepository = urgenciaRepository;
        this.pacientes = new ListaSimple<>();
        this.mapPorCurp = new MyHashMap<>();
        this.citas = new ListaSimple<>();
        this.doctores = new ListaSimple<>();
        this.doctoresUrgencias = new ListaSimple<>();;
        this.urgenciasHeap = new MinHeap(16);
        this.urgenciasAtendidas = new Pila<>();
        this.citasActivas = new Cola<>();
    }
    @PostConstruct
    public void init() {
        // Esto se ejecuta AUTOMÁTICAMENTE una sola vez al arrancar la app
        pacientes.addAll(pacienteRepository.findAll());
        for (Paciente paciente : pacientes) {
            mapPorCurp.put(paciente.getCurp().toLowerCase(), paciente);
        }
        citas = citaRepository.findByOrderByFechaDescHoraAsc();
        ListaSimple<Cita> citasActuales = citaRepository.findByFechaOrderByHoraAsc(
                LocalDate.now().toString(),
                "SOLICITADA"
        );
        for(Cita cita : citasActuales){
            citasActivas.enqueue(cita);
        }
        doctores.addAll(doctorRepository.findAll());
        doctoresUrgencias.addAll(doctorRepository.buscarDisponibles("Urgencias", true));
        urgenciasHeap.insert(urgenciaRepository.getUrgenciaAndDoctor());
        urgenciasAtendidas.push(urgenciaRepository.getAtendidas());
    }

    /**
     * Este metodo guarda una instancia de {@code Paciente} en la base de datos.
     * Almacena la instancia en una estructura
     * {@code MyHashMap<String, Paciente>} utilizando la CURP del paciente como clave.
     * @param paciente instancia a guardar.
     */
    public void guardarPaciente(Paciente paciente) {
        Paciente guardado = pacienteRepository.save(paciente);
        pacientes.add(guardado);
        mapPorCurp.put(paciente.getCurp().toLowerCase(), guardado);
    }

    /**
     * Este metodo elimina un registro en la base de datos de la entidad {@code Paciente}.
     * Se asume que ya se validó la existencia del registro en capas anteriores,
     * por lo que este metodo no lo valida.
     * Elimina el registro correspondiente de {@code MyHashMap<String, Paciente>}.
     * @param paciente instancia a eliminar.
     */
    public void eliminarPaciente(Paciente paciente) {
        pacientes.remove(paciente);
        pacienteRepository.deleteById(paciente.getId());
        mapPorCurp.remove(paciente.getCurp().toLowerCase());
    }

    /**
     * Este metodo guarda una instancia de {@code Cita} en la base de datos.
     * Agrega la instancia registrada al atributo {@code ListaSimple<Cita> Citas }
     * para mantener la persistencia de datos.
     * @param cita instancia a guardar.
     */
    public void guardarCita(Cita cita) {
        cita.setPacienteCurp(cita.getPacienteCurp());
        Cita guardado = citaRepository.save(cita);
        citas.add(guardado);
        LocalDate fecha = LocalDate.parse(cita.getFecha());
        if(fecha.isEqual(LocalDate.now())) {
            citasActivas.enqueue(cita);
        }
    }

    /**
     * Este metodo atiende la cita al inicio de {@code Cola<Cita> citasActivas}.
     * @return {@code 200} si se encontró los registros de la cita y del paciente correspondiente,
     * {@code 404} si cualquiera de los dos registros no se encontró.
     */
    public int atenderCita() {
        if(citasActivas.isEmpty()) return 404;
        Cita cita = citasActivas.peek();
        System.out.println(cita);
        Optional<Cita> existente = citaRepository.findById(cita.getId());
        Optional<Paciente> paciente = pacienteRepository.findByCurp(cita.getPacienteCurp());
        ListaSimple<Doctor> doctores = doctorRepository.buscarDisponibles(cita.getDepartamento(), true);
        System.out.println(doctores.size());
        if(existente.isPresent() && paciente.isPresent() && doctores.size() > 0) {
            Random  random = new Random();
            Doctor doctor = doctores.get(random.nextInt(doctores.size()));

            cita.setDoctor(doctor);
            actualizarDisponible(doctor, 0);
            cita.setEstado("ATENDIDA");
            citaRepository.save(cita);
            citasActivas.dequeue();
            return 200;
        } else {
            return 404;
        }
    }

    /**
     * Este metodo obtiene la lista de citas registrada para un paciente.
     * @param paciente instancia del paciente al que se van a obtener su lista de citas.
     * @return Lista de citas registradas del paciente {@code ListaSimple<Cita>}.
     */
    public ListaSimple<Cita> obtenerCitasPorPaciente(Paciente paciente) {
        return citaRepository.findByCurp(paciente.getCurp());
    }
    /**
     * Este metodo guarda una instancia de {@code Doctor} en la base de datos.
     * Agrega la instancia registrada al atributo {@code ListaSimple<Doctor> doctores }
     * para mantener la persistencia de datos.
     * @param doctor instancia a guardar.
     */
    public void guardarDoctor(Doctor doctor) {
        Doctor guardado = doctorRepository.save(doctor);
        doctores.add(guardado);
    }
    /**
     * Este metodo revisa si un doctor existe.
     * @param doctorId id del doctor a buscar.
     * @return una instancia de {@code Doctor} si se encontró o {@code null} en caso contrario.
     * */
    public Doctor fetchDoctor(Long doctorId) {
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        return doctor.orElse(null);
    }
    /**
     * Este metodo revisa si un paciente existe.
     * @param pacienteId id del doctor a buscar.
     * @return una instancia de {@code Paciente} si se encontró o {@code null} en caso contrario.
     * */
    public Paciente fetchPaciente(Long pacienteId) {
        Optional<Paciente> paciente = pacienteRepository.findById(pacienteId);
        return paciente.orElse(null);
    }
    /**
     * Este metodo actualiza la disponibilidad de una instancia {@code Doctor} en la base de datos.
     * Agrega la instancia registrada al atributo {@code ListaSimple<Cita> doctoresUrgencias }
     * para mantener la persistencia de datos.
     * @param doctor instancia a actualizar.
     * @param disponible {@code Integer}, si se desea cambiar {@code disponible} a {@code true}
     * se pasa como argumento {@code 1}, en caso contrario {@code 0}.
     */
    public void actualizarDisponible(Doctor doctor, Integer disponible){
        int indiceEnLista = doctores.indexOf(doctor); // Obtenemos el índice de la lista en memoria
        doctor.setDisponible(disponible != 0); // Cambiamos la disponibilidad
        doctores.set(indiceEnLista, doctor); // Actualizamos la lista en memoria
        if(doctor.getEspecialidad().equalsIgnoreCase("Urgencias")){
            if(disponible == 1){
                doctoresUrgencias.add(doctor);
            } else {
                doctoresUrgencias.remove(doctor);
            }
        }
        doctorRepository.save(doctor);
    }
    /**
     * Este metodo guarda una instancia de {@code Urgencia} en la base de datos.
     * Agrega la instancia registrada al atributo {@code MinHeap urgenciasHeap}
     * para mantener actualizado el listado de urgencias.
     * @param dto {@code CreateUrgenciaDTO} datos iniciales para la creación.
     * @param doctor {@code Doctor} doctor a cargo de la urgencia.
     */
    public void guardarUrgencia(CreateUrgenciaDTO dto, Doctor doctor) {
        Urgencia urgencia = new Urgencia();
        if(doctor != null) urgencia.setDoctor(doctor);
        urgencia.setPrioridad(dto.getPrioridad());
        urgencia.setPacienteCurp(dto.getPacienteCurp());
        urgencia.setFechaRegistro(dto.getFechaRegistro());
        Urgencia guardado = urgenciaRepository.save(urgencia);
        urgenciasHeap.insert(guardado);
    }
    /**
     * Este metodo actualiza el estado de una instancia de {@code Urgencia} en la base de datos.
     * Elimina la instancia del atributo {@code MinHeap urgenciasHeap}, al mismo tiempo que
     * agrega la instancia registrada al atributo {@code Pila<Urgencia> urgenciasAtendidas }
     * para mantener la coherencia de datos.
     * @param id {@code Long} id de la instancia a actualizar, ya se programó para que
     * {@code id} corresponda con la {@code Urgencia} de mayor prioridad en {@code MinHeap urgenciasHeap}.
     */
    public int atenderUrgencia(Long id) {
        Optional<Urgencia> urgencyOpt = urgenciaRepository.findById(id);
        if(urgencyOpt.isEmpty()){
            return 404;
        }
        Urgencia urgencia = urgencyOpt.get();
        LocalDateTime momentoRegistro = LocalDateTime.now();
        urgencia.setFechaDeAlta(momentoRegistro);
        urgencia.setAtendida(true);
        try {
            urgenciaRepository.save(urgencia);
        } catch (Exception ex) {
            return 404;
        }
        Urgencia atendida = urgenciasHeap.remove();
        atendida.setFechaDeAlta(momentoRegistro);
        urgenciasAtendidas.push(atendida);
        return 200;
    }
}

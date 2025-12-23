package com.example.sistemas.stailenceback.service.impl;

import com.example.sistemas.stailenceback.dto.request.CreateAppointmentRequest;
import com.example.sistemas.stailenceback.dto.response.AppointmentResponse;
import com.example.sistemas.stailenceback.domain.entities.Cita;
import com.example.sistemas.stailenceback.domain.entities.Negocio;
import com.example.sistemas.stailenceback.domain.entities.Servicio;
import com.example.sistemas.stailenceback.domain.entities.Usuario;
import com.example.sistemas.stailenceback.exception.BusinessException;
import com.example.sistemas.stailenceback.exception.ResourceNotFoundException;
import com.example.sistemas.stailenceback.repository.CitaRepository;
import com.example.sistemas.stailenceback.repository.NegocioRepository;
import com.example.sistemas.stailenceback.repository.ServicioRepository;
import com.example.sistemas.stailenceback.repository.UsuarioRepository;
import com.example.sistemas.stailenceback.service.AppointmentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final CitaRepository citaRepository;
    private final NegocioRepository negocioRepository;
    private final ServicioRepository servicioRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public AppointmentServiceImpl(CitaRepository citaRepository, NegocioRepository negocioRepository, ServicioRepository servicioRepository, UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.citaRepository = citaRepository;
        this.negocioRepository = negocioRepository;
        this.servicioRepository = servicioRepository;
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public AppointmentResponse createAppointment(CreateAppointmentRequest req) {
        Negocio n = negocioRepository.findById(req.getNegocioId()).orElseThrow(() -> new ResourceNotFoundException("Negocio no encontrado"));
        Servicio s = servicioRepository.findById(req.getServicioId()).orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado"));
        Usuario cliente = usuarioRepository.findById(req.getClienteUsuarioId()).orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        Usuario empleado = null;
        if (req.getEmpleadoUsuarioId() != null) {
            empleado = usuarioRepository.findById(req.getEmpleadoUsuarioId()).orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado"));
        }

        // Validación simple: verificar conflictos
        var citasHoy = citaRepository.findByNegocioIdAndFechaProgramada(n.getId(), req.getFechaProgramada());
        // convertir hora inicio y verificar conflictos por solapamiento
        LocalTime inicio = req.getHoraInicioProgramada();
        LocalTime fin = inicio.plusMinutes(s.getDuracionMinutos());
        boolean conflict = citasHoy.stream().anyMatch(c -> {
            LocalTime cInicio = c.getHoraInicioProgramada();
            LocalTime cFin = c.getHoraFinProgramada();
            return inicio.isBefore(cFin) && fin.isAfter(cInicio);
        });
        if (conflict) throw new BusinessException("Conflicto de horario para el negocio en esa franja");

        Cita cita = Cita.builder()
                .uuid(UUID.randomUUID().toString())
                .negocio(n)
                .servicio(s)
                .clienteUsuario(cliente)
                .empleadoUsuario(empleado)
                .fechaProgramada(req.getFechaProgramada())
                .horaInicioProgramada(req.getHoraInicioProgramada())
                .horaFinProgramada(fin)
                .estado("reservada")
                .build();

        citaRepository.save(cita);
        return modelMapper.map(cita, AppointmentResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByClient(Long clienteId) {
        return citaRepository.findByClienteUsuarioId(clienteId).stream()
                .map(c -> modelMapper.map(c, AppointmentResponse.class))
                .collect(Collectors.toList());
    }
}


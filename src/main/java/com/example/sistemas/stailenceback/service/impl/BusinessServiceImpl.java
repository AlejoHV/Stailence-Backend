package com.example.sistemas.stailenceback.service.impl;

import com.example.sistemas.stailenceback.dto.request.CreateBusinessRequest;
import com.example.sistemas.stailenceback.dto.response.*;
import com.example.sistemas.stailenceback.domain.entities.Negocio;
import com.example.sistemas.stailenceback.domain.entities.RolesUsuarioNegocio;
import com.example.sistemas.stailenceback.domain.entities.Usuario;
import com.example.sistemas.stailenceback.exception.ResourceNotFoundException;
import com.example.sistemas.stailenceback.repository.CitaRepository;
import com.example.sistemas.stailenceback.repository.NegocioRepository;
import com.example.sistemas.stailenceback.repository.RolesUsuarioNegocioRepository;
import com.example.sistemas.stailenceback.repository.UsuarioRepository;
import com.example.sistemas.stailenceback.service.BusinessService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BusinessServiceImpl implements BusinessService {

    private final NegocioRepository negocioRepository;
    private final ModelMapper modelMapper;
    private final UsuarioRepository usuarioRepository;
    private final RolesUsuarioNegocioRepository rolesUsuarioNegocioRepository;
    private final CitaRepository citaRepository;

    public BusinessServiceImpl(NegocioRepository negocioRepository, ModelMapper modelMapper, UsuarioRepository usuarioRepository, RolesUsuarioNegocioRepository rolesUsuarioNegocioRepository, CitaRepository citaRepository) {
        this.negocioRepository = negocioRepository;
        this.modelMapper = modelMapper;
        this.usuarioRepository = usuarioRepository;
        this.rolesUsuarioNegocioRepository = rolesUsuarioNegocioRepository;
        this.citaRepository = citaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public BusinessResponse getProfile(Long negocioId) {
        Negocio n = negocioRepository.findById(negocioId)
                .orElseThrow(() -> new ResourceNotFoundException("Negocio no encontrado"));
        return modelMapper.map(n, BusinessResponse.class);
    }

    @Override
    @Transactional
    public BusinessResponse updateProfile(Long negocioId, CreateBusinessRequest req) {
        Negocio n = negocioRepository.findById(negocioId)
                .orElseThrow(() -> new ResourceNotFoundException("Negocio no encontrado"));
        n.setNombre(req.getNombre());
        n.setDireccion(req.getDireccion());
        n.setCorreo(req.getCorreo());
        negocioRepository.save(n);
        return modelMapper.map(n, BusinessResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> listEmployees(Long negocioId) {
        List<RolesUsuarioNegocio> roles = rolesUsuarioNegocioRepository.findAll().stream().filter(r -> r.getNegocio().getId().equals(negocioId)).collect(Collectors.toList());
        return roles.stream().map(r -> modelMapper.map(r.getUsuario(), UserResponse.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserResponse createEmployee(Long negocioId, UserResponse req) {
        Negocio n = negocioRepository.findById(negocioId).orElseThrow(() -> new ResourceNotFoundException("Negocio no encontrado"));
        Usuario u = Usuario.builder()
                .uuid(UUID.randomUUID().toString())
                .correo(req.getCorreo())
                .nombres(req.getNombres())
                .apellidos(req.getApellidos())
                .hashContrasena("")
                .build();
        usuarioRepository.save(u);
        RolesUsuarioNegocio rel = RolesUsuarioNegocio.builder().negocio(n).usuario(u).rol("employee").build();
        rolesUsuarioNegocioRepository.save(rel);
        return modelMapper.map(u, UserResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> listAppointments(Long negocioId) {
        return citaRepository.findByNegocioIdAndFechaProgramada(negocioId, java.time.LocalDate.now()).stream()
                .map(c -> modelMapper.map(c, AppointmentResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BusinessDetailResponse getDetail(Long negocioId) {
        Negocio n = negocioRepository.findById(negocioId).orElseThrow(() -> new ResourceNotFoundException("Negocio no encontrado"));
        BusinessDetailResponse resp = modelMapper.map(n, BusinessDetailResponse.class);
        resp.setServicios(n.getServicios() != null ? n.getServicios().stream().map(s -> modelMapper.map(s, ServiceResponse.class)).collect(Collectors.toList()) : List.of());
        resp.setEmpleados(listEmployees(negocioId));
        return resp;
    }
}

package com.example.sistemas.stailenceback.service.impl;

import com.example.sistemas.stailenceback.dto.request.AssignRoleRequest;
import com.example.sistemas.stailenceback.dto.request.CreateBusinessRequest;
import com.example.sistemas.stailenceback.dto.response.*;
import com.example.sistemas.stailenceback.domain.entities.MembershipRole;
import com.example.sistemas.stailenceback.domain.entities.Negocio;
import com.example.sistemas.stailenceback.domain.entities.RolesUsuarioNegocio;
import com.example.sistemas.stailenceback.domain.entities.Usuario;
import com.example.sistemas.stailenceback.exception.ResourceNotFoundException;
import com.example.sistemas.stailenceback.exception.UnauthorizedException;
import com.example.sistemas.stailenceback.repository.CitaRepository;
import com.example.sistemas.stailenceback.repository.NegocioRepository;
import com.example.sistemas.stailenceback.repository.RolesUsuarioNegocioRepository;
import com.example.sistemas.stailenceback.repository.UsuarioRepository;
import com.example.sistemas.stailenceback.service.BusinessService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
        List<RolesUsuarioNegocio> roles = rolesUsuarioNegocioRepository.findByNegocioId(negocioId);
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
        RolesUsuarioNegocio rel = RolesUsuarioNegocio.builder().negocio(n).usuario(u).rol(MembershipRole.STAFF).build();
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

    @Override
    @Transactional(readOnly = true)
    public List<BusinessResponse> listAllBusinesses() {
        return negocioRepository.findAll().stream().map(n -> modelMapper.map(n, BusinessResponse.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BusinessResponse createBusiness(CreateBusinessRequest req) {
        // Obtener autenticación
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new UnauthorizedException("Usuario no autenticado");
        }

        String username = auth.getName();
        Usuario user = usuarioRepository.findByCorreo(username).orElseThrow(() -> new ResourceNotFoundException("Usuario autenticado no encontrado"));

        // Generar UUID
        String uuid = UUID.randomUUID().toString();

        // Si slug es vacío o nulo, generar uno simple basado en nombre
        String slug = req.getSlug();
        if (slug == null || slug.isBlank()) {
            slug = req.getNombre().toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("(^-|-$)", "");
            String base = slug;
            int i = 1;
            while (negocioRepository.existsBySlug(slug)) {
                slug = base + "-" + i++;
            }
        } else {
            if (negocioRepository.existsBySlug(slug)) {
                throw new IllegalArgumentException("Slug ya existe");
            }
        }

        Negocio n = Negocio.builder()
                .uuid(uuid)
                .nombre(req.getNombre())
                .nombreLegal(req.getNombreLegal())
                .slug(slug)
                .correo(req.getCorreo())
                .telefono(req.getTelefono())
                .direccion(req.getDireccion())
                .build();

        negocioRepository.save(n);

        RolesUsuarioNegocio rel = RolesUsuarioNegocio.builder().negocio(n).usuario(user).rol(MembershipRole.OWNER).build();
        rolesUsuarioNegocioRepository.save(rel);

        return modelMapper.map(n, BusinessResponse.class);
    }

    @Override
    @Transactional
    public UserResponse assignRole(Long negocioId, AssignRoleRequest req) {
        Negocio n = negocioRepository.findById(negocioId).orElseThrow(() -> new ResourceNotFoundException("Negocio no encontrado"));
        Usuario u = usuarioRepository.findById(req.getUsuarioId()).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        MembershipRole roleEnum;
        try {
            roleEnum = MembershipRole.valueOf(req.getRol().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Rol inválido. Valores permitidos: OWNER, MANAGER, STAFF");
        }

        Optional<RolesUsuarioNegocio> existing = rolesUsuarioNegocioRepository.findByNegocioIdAndUsuarioId(n.getId(), u.getId());
        RolesUsuarioNegocio rel;
        if (existing.isPresent()) {
            rel = existing.get();
            rel.setRol(roleEnum);
        } else {
            rel = RolesUsuarioNegocio.builder().negocio(n).usuario(u).rol(roleEnum).build();
        }
        rolesUsuarioNegocioRepository.save(rel);
        return modelMapper.map(u, UserResponse.class);
    }

    @Override
    @Transactional
    public void removeRole(Long negocioId, Long usuarioId) {
        Negocio n = negocioRepository.findById(negocioId).orElseThrow(() -> new ResourceNotFoundException("Negocio no encontrado"));
        RolesUsuarioNegocio rel = rolesUsuarioNegocioRepository.findByNegocioIdAndUsuarioId(n.getId(), usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Relación rol-usuario no encontrada para ese negocio"));

        // No permitir eliminar el último OWNER
        if (rel.getRol() == MembershipRole.OWNER) {
            long owners = rolesUsuarioNegocioRepository.countByNegocioIdAndRol(n.getId(), MembershipRole.OWNER);
            if (owners <= 1) {
                throw new IllegalStateException("No se puede eliminar el último OWNER del negocio");
            }
        }

        rolesUsuarioNegocioRepository.delete(rel);
    }
}

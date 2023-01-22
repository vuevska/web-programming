package mk.ukim.finki.wp.june2022.g1.service.impl;

import mk.ukim.finki.wp.june2022.g1.model.OSType;
import mk.ukim.finki.wp.june2022.g1.model.User;
import mk.ukim.finki.wp.june2022.g1.model.VirtualServer;
import mk.ukim.finki.wp.june2022.g1.model.exceptions.InvalidUserIdException;
import mk.ukim.finki.wp.june2022.g1.model.exceptions.InvalidVirtualMachineIdException;
import mk.ukim.finki.wp.june2022.g1.repository.UserRepository;
import mk.ukim.finki.wp.june2022.g1.repository.VirtualServerRepository;
import mk.ukim.finki.wp.june2022.g1.service.VirtualServerService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class VirtualServerServiceImpl implements VirtualServerService {

    private final VirtualServerRepository virtualServerRepository;
    private final UserRepository userRepository;

    public VirtualServerServiceImpl(VirtualServerRepository virtualServerRepository, UserRepository userRepository) {
        this.virtualServerRepository = virtualServerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<VirtualServer> listAll() {
        return this.virtualServerRepository.findAll();
    }

    @Override
    public VirtualServer findById(Long id) {
        return this.virtualServerRepository.findById(id).orElseThrow(InvalidVirtualMachineIdException::new);
    }

    @Override
    public VirtualServer create(String name, String ipAddress, OSType osType, List<Long> owners, LocalDate launchDate) {
        List<User> users = this.userRepository.findAllById(owners);
        VirtualServer virtualServer = new VirtualServer(name, ipAddress, osType, users, launchDate);
        this.virtualServerRepository.save(virtualServer);
        return virtualServer;
    }

    @Override
    public VirtualServer update(Long id, String name, String ipAddress, OSType osType, List<Long> owners) {
        VirtualServer virtualServer = this.virtualServerRepository
                .findById(id)
                .orElseThrow(InvalidVirtualMachineIdException::new);
        virtualServer.setInstanceName(name);
        virtualServer.setIpAddress(ipAddress);
        virtualServer.setOSType(osType);
        List<User> users = this.userRepository.findAllById(owners);
        virtualServer.setOwners(users);
        this.virtualServerRepository.save(virtualServer);
        return virtualServer;
    }

    @Override
    public VirtualServer delete(Long id) {
        VirtualServer virtualServer = this.virtualServerRepository
                .findById(id)
                .orElseThrow(InvalidVirtualMachineIdException::new);
        this.virtualServerRepository.delete(virtualServer);
        return virtualServer;
    }

    @Override
    public VirtualServer markTerminated(Long id) {
        VirtualServer virtualServer = this.virtualServerRepository
                .findById(id)
                .orElseThrow(InvalidVirtualMachineIdException::new);
        virtualServer.setTerminated(true);
        this.virtualServerRepository.save(virtualServer);
        return virtualServer;
    }

    /*Потребно е да овозможите пребарување
     на виртуелни машини според owner и денови активност (launchDate <= (now - filtering days))*/
    @Override
    public List<VirtualServer> filter(Long ownerId, Integer activeMoreThanDays) {
        if (ownerId != null && activeMoreThanDays != null) {
            return this.virtualServerRepository.findByOwnersContainingAndLaunchDateBefore(
                    this.userRepository.findById(ownerId).orElseThrow(InvalidUserIdException::new),
                    LocalDate.now().minusDays(activeMoreThanDays)
            );
        } else if (ownerId != null) {
            return this.virtualServerRepository.findByOwnersContaining(
                    this.userRepository
                            .findById(ownerId)
                            .orElseThrow(InvalidUserIdException::new)
            );
        } else if (activeMoreThanDays != null) {
            return this.virtualServerRepository
                    .findByLaunchDateBefore(
                            LocalDate.now().minusDays(activeMoreThanDays)
                    );
        }
        return this.virtualServerRepository.findAll();
    }
}

package com.mindhub.homebanking.services.implement;


import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ClientServiceImplement implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    //Traer lista de clientes DTO
    @Override
    public List<ClientDTO> getClients() {
        return  clientRepository.findAll()
                .stream()
                .map(currentClient -> new ClientDTO(currentClient))
                .collect(toList());
    }


    //Trae el cliente por id
    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    //Trae el clientDTO por id
    @Override
    public ClientDTO getClientById(Long id) {
        return new ClientDTO(this.findById(id));
    }

    //verifica el cliente con el email

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);

    }

    //trae clientDTO autenticado
    @Override
    public ClientDTO getClientCurrentAll(String email) {
        return new ClientDTO(this.findByEmail(email));
    }


    //agrego un cliente en el repo
    @Override
    public void clientAdd(Client client) {
        clientRepository.save(client);
    }

    //guardo el cliente
    @Override
    public void clientSave(Client client) {
        clientRepository.save(client);
    }


}

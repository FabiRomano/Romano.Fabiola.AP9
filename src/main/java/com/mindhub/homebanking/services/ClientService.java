package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import java.util.List;

public interface ClientService {

    //Traer lista de clientes DTO
    List<ClientDTO> getClients();


    //Trae el cliente por id
    Client findById(Long id);


    //Trae clientDto por id
    ClientDTO getClientById( Long id);


    //verificca el cliente con el email
    Client findByEmail (String email);


    //trae clienteDTO autenticado
    ClientDTO getClientCurrentAll (String email);

    //agrego un cliente en el repo
    void clientAdd(Client client);

    //guardo el cliente
    void clientSave(Client client);

}

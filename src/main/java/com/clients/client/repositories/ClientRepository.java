package com.clients.client.repositories;

import com.clients.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;

interface ClientRepository extends JpaRepository<Client, Long> {

}
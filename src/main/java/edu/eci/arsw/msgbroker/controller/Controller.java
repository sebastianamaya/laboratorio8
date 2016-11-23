package edu.eci.arsw.msgbroker.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import edu.eci.arsw.msgbroker.controller.Manejador;
import edu.eci.arsw.msgbroker.model.Point;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Owner
 */
@RestController
@RequestMapping(value = "/puntos")
public class Controller {
    @Autowired
    SimpMessagingTemplate msgt;
    @Autowired
    Manejador manejador;
    private BlockingQueue puntos;
     @RequestMapping(method = RequestMethod.POST)    
    public ResponseEntity<?> manejadorPost(@RequestBody Point punto){
    try {
            puntos = manejador.getPuntos();
            puntos.add(punto);
            if(puntos.size()==4){
                msgt.convertAndSend("/topic/newpolygon", puntos);
                puntos.clear();
            }        
            System.out.println("Nuevo punto recibido en el servidor post!:"+punto);
            msgt.convertAndSend("/topic/newpoint", punto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error no se recibio ningun punto",HttpStatus.FORBIDDEN);            
        }        

    }
    
    }


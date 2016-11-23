/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.msgbroker.model;

import edu.eci.arsw.msgbroker.controller.Manejador;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Georgios
 */
@Controller
public class STOMPMessagesHandler {

    @Autowired
    SimpMessagingTemplate msgt;
    @Autowired
    Manejador manejador;
     private BlockingQueue puntos;
    //List<Point> Puntos = new ArrayList<>();
    @MessageMapping("/newpoint")    
    public void getLine(Point pt) throws Exception {
        System.out.println("Nuevo punto recibido en el servidor!:"+pt);
        puntos=manejador.getPuntos();
        puntos.add(pt);
       // NumPuntos= NumPuntos+1;
        msgt.convertAndSend("/app/newpoint", pt);
        if(puntos.size()==4){
            System.out.println("new event");
            msgt.convertAndSend("/topic/newpolygon", puntos);
            System.out.println("==>4 puntos hechos!");
            //NumPuntos=0;
            puntos.clear();
        }
        
    }
    
    
}

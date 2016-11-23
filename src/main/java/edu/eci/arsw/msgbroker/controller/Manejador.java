/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.msgbroker.controller;

import edu.eci.arsw.msgbroker.model.Point;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.springframework.stereotype.Service;

/**
 *
 * @author Owner
 */
@Service
public class Manejador {
    public Manejador (){
        puntos=new ArrayBlockingQueue<Point>(4);
    }
    public BlockingQueue getPuntos() {
        return puntos;
    }
private BlockingQueue puntos;

   
    
}

package com.example.test.design.model.proxy._静态代理;

import com.example.test.design.model.proxy._静态代理.Movie;
import com.example.test.design.model.proxy._静态代理.RealMovie;
import com.example.test.design.model.proxy._静态代理.WandaCinema;

/**
 * @Description 
 * @author leiel
 * @Date 2020/6/12 8:46 AM
 */

public class WandaCinemaTest {

    public static void main(String[] args) {
        RealMovie realmovie = new RealMovie();

        Movie movie = new WandaCinema(realmovie);

        movie.play();
    }

}

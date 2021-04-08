package com.example.test.design.model.proxy._静态代理;


/**
 * @Description 万达电影院代理播放电影
 * @author leiel
 * @Date 2020/6/12 8:42 AM
 */

public class WandaCinema implements Movie {

    RealMovie movie;

    public WandaCinema(RealMovie movie) {
        super();
        this.movie = movie;
    }

    @Override
    public void play() {
        advertisement(true);

        movie.play();

        advertisement(false);

    }

    public void advertisement(boolean isStart){
        if ( isStart ) {
            System.out.println("电影马上开始了，爆米花、可乐、口香糖9.8折，快来买啊！");
        } else {
            System.out.println("电影马上结束了，爆米花、可乐、口香糖9.8折，买回家吃吧！");
        }
    }
}

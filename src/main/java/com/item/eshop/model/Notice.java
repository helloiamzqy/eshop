package com.item.eshop.model;


public class Notice {

    private  String title;

    private  Integer id;

    private  String content;

    private  Integer category;

    private  String times;

    private  int status;

    private  String image;

    private  Integer foreign_id;



    public  String  getTitle(){
        return  this.title;
    };
    public  void  setTitle(String title){
        this.title=title;
    }

    public  Integer  getId(){
        return  this.id;
    };
    public  void  setId(Integer id){
        this.id=id;
    }

    public  String  getContent(){
        return  this.content;
    };
    public  void  setContent(String content){
        this.content=content;
    }

    public  Integer  getCategory(){
        return  this.category;
    };
    public  void  setCategory(Integer category){
        this.category=category;
    }

    public  String  getTimes(){
        return  this.times;
    };
    public  void  setTimes(String times){
        this.times=times;
    }

    public  int  getStatus(){
        return  this.status;
    };
    public  void  setStatus(int status){
        this.status=status;
    }

    public  String  getImage(){
        return  this.image;
    };
    public  void  setImage(String image){
        this.image=image;
    }

    public  long  getForeign_id(){
        return  this.foreign_id;
    };
    public  void  setForeign_id(Integer foreign_id){
        this.foreign_id=foreign_id;
    }


}
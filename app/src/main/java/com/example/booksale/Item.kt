package com.example.booksale

class Item {
    var BookName:String? =null
    var Edit: String? =null
    var Author:String?= null
    var Publisher: String?=null
    var HopePrice:String?= null

    constructor()
    constructor(BookName : String?, Edit : String?, Author: String?, Publisher: String?, HopePrice:String?){
        this.BookName = BookName
        this.Edit = Edit
        this.Author = Author
        this.Publisher = Publisher
        this.HopePrice = HopePrice
    }
}
class Paramen{
    String name
    ArrayList choices
    String value
    String type
    String defaultValue
    String class
    String description
    
    Paramen(String name,
            ArrayList choices = new ArrayList()
    ){
        this.name = name
        this.choices = choices
    }

    void printName(){
        echo this.name
    }
}
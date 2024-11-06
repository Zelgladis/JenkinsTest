class Paramen{
    String name
    List choices
    String value
    Boolean valueBool
    String type
    String defValue
    String parclass
    String description
    
    Paramen(Map args)
    {
        this.name = args.get('name', 'Null')
        this.choices = args.get('choices', 'Null')
        this.choices = args.get('value', 'Null')
        this.choices = args.get('valueBool', false)
        this.choices = args.get('type', 'Null')
        this.choices = args.get('defValue', 'Null')
        this.choices = args.get('parclass', 'Null')
        this.choices = args.get('description', 'Null')
    }

    param_ret(nvalue=''){
        if(nvalue == ''){
            nvalue = this.value
        }
        switch (type) {
            case 'string':
                return booleanParam(name: this.name, defaultValue: this.valueBool, description: this.description)
                break
            default:
                return "Ни одно из условий не подошло"
        }
    }
}
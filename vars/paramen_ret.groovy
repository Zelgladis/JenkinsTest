def call(Map args){
    pname = args.get('name', 'Null')
    choices = args.get('choices', 'Null')
    value = args.get('value', 'Null')
    valueBool = args.get('valueBool', false)
    type = args.get('type', 'Null')
    defValue = args.get('defValue', 'Null')
    parclass = args.get('parclass', 'Null')
    description = args.get('description', 'Null')

    if(nvalue == ''){
            nvalue = value
        }
        switch (type) {
            case 'string':
                return booleanParam(name: pname, defaultValue: valueBool, description: description)
                break
            default:
                return "Ни одно из условий не подошло"
    }
}
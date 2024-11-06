def call(Map args){
    def ret
    pname = args.get('name', 'Null')
    choices = args.get('choices', 'Null')
    value = args.get('value', 'Null')
    valueBool = args.get('valueBool', false)
    type = args.get('type', 'Null')
    defValue = args.get('defValue', 'Null')
    parclass = args.get('parclass', 'Null')
    description = args.get('description', 'Null')

    switch (type) {
        case 'bool':
            ret = booleanParam(name: pname, defaultValue: valueBool, description: description)
            break
        default:
            ret = "Ни одно из условий не подошло"
    return ret
    }
}
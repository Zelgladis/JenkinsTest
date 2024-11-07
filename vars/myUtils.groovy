def rekurwa(String stroka, int i=0 ,List<String> res=[]){
    sp_str = stroka.split('/')
    if(i != sp_str.size()-1){
        res.add(sp_str[0..i].join('/'))
        return rekurwa(stroka, i+1, res)
    }else{
        return res
    }
}
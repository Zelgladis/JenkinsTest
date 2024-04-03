class N_Class{
    HashMap envs

    N_Class(String var){
        envs = new HashMap()
        envs.put('var', 'TestString')
        envs.put('name', 'Sergey')
        envs.put('yami', 'Darkness')
        envs.put('d2', 'default')

        if(var == 'true'){
            envs.put('loc', 'ru')
        }else{
            envs.put('loc', 'eng')
        }
    }

    void add_env(String mkey, String mvalue){
        envs.put(mkey, mvalue)
    }

    HashMap get_envs(){
        return envs
    }
}
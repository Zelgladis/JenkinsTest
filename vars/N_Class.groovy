class N_Class{
    HashMap envs

    N_Class(String var){
        envs = new HashMap()
        envs.put('JAVA_HOME', '/home/mio/dist/jdk-11.0.2')
        envs.put('GIT_USERNAME', 'zelgladis')

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
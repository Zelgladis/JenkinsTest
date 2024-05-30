def call(){
    node(){
        stage('haha'){
            if(env.label != null){
                env.topri = env.label
            }
            echo env.topri
            echo env.label
        }
    }  
}


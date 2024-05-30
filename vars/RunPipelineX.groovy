def call(Map args){
    node(){
        stage('RunPipeX'){
            args.each { arg ->
                echo "$arg"
            }
            params.each { para ->
                echo "$para"
            }
        }
    }
    
}
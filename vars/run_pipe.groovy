// vars/run.pipe.groovy

def call(String br){
    try{
        mpline.init_env()
        mpline.git_pull()
    }catch(Exception e){
        throw new Exception("Something went wrong!")
        
    }finally{
        mpline.clean()
    }
}

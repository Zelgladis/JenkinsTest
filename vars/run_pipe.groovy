// vars/run.pipe.groovy

def call(String br){
    try{
        mpline.init_env()
        mpline.step1(br)
        //mpline.failed_step()
        mpline.step_print()
    }catch(Exception e){
        throw new Exception("Something went wrong!")
        
    }finally{
        mpline.clean()
    }
}

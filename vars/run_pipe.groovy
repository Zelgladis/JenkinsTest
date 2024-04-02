// vars/run.pipe.groovy

def call(){
    try{
        mpline.init_env()
        mpline.step1(BRANCH_NAME)
        //mpline.failed_step()
        mpline.step_print()
    }catch(Exception e){
        throw new Exception("Something went wrong!")
        
    }finally{
        mpline.clean()
    }
}

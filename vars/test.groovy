// vars/test.groovy
import come.VarsM

def call() {
  echo 'Test Lib'
  def n_mclass = new MyClass('Hello world')
  echo "${n_mclass.yami}"
  echo "${n_mclass.d2}"
  echo depl


  def depl = VarsM[$]
  
}

def test() {
  def str = 'DEV ST'
  def konf_lst = str.split(' ')
  def depl = ''
  for (i in konf_lst) {
    depl = VarsM[i]
    stage("deploy to $depl"){
        println "Iteration: $depl"
    }
  }
  
}
// vars/test.groovy

def call(String x) {
  echo 'Test Lib'
  def n_mclass = new MyClass('Hello world')
  echo "${n_mclass.yami}"
  echo "${n_mclass.d2}"
  echo x
}

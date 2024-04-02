// vars/test.groovy

def call(Boolean x) {
  echo 'Test Lib'
  def n_mclass = new MyClass('Hello world')
  echo "${n_mclass.yami}"
  echo "${n_mclass.d2}"
  echo x
}

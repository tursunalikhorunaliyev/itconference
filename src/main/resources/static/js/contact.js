const api = 'http://localhost:8080/it-conference-kuva/auth/register'

const fam = document.getElementById('fam')
const tel = document.getElementById('tel')
const username = document.getElementById('ism')

let form = document.getElementById('form')

let userLogin = JSON.parse(localStorage.getItem('list')) ? JSON.parse(localStorage.getItem('list')) : []

const phoneMaskSelector = '.js-phone-input';
const phoneMaskInputs = document.querySelectorAll(phoneMaskSelector);

const masksOptions = {
  phone: {
    mask: '(00) 000-00-00'
  }
};

for(const item of phoneMaskInputs) {
  new IMask(item, masksOptions.phone);
}



form.addEventListener('submit', (e) => {
  e.preventDefault()

  //User Agent

  function isMobile() {
    return navigator.maxTouchPoints > 0 && /Android|iphone|ipad/i.test(navigator.userAgent)

  }
  isMobile()

  let user = navigator.userAgent.split('')
  let userIndex = user.indexOf('(')
  let userLastIndex = user.indexOf(')')
  console.log(userIndex, userLastIndex);
  let userSlice = user.slice(userIndex + 1, userLastIndex)
   let finishUser =  userSlice.join('')


  // form data
  let ism = username.value
  let famEl = fam.value
  let telEl = tel.value
  console.log(ism, famEl, telEl);

  let pload = new FormData(form)

  pload.append("firstname", ism)
  pload.append("lastname", famEl)
  pload.append("phone", telEl)
  pload.append("device", finishUser)
  console.log(pload);

  fetch(api, {
    method: 'POST',
    body: pload,
  }).then((user) => {
    return user.json()
  }).then((item) => {

    let message = item.message
    let status = item.status


    if (status == true) {
      let cardId = item.data.generated.cardID
      userLogin.push({ "renderId": cardId })
      console.log(userLogin);
      localStorage.setItem('list', JSON.stringify(userLogin))
      window.open('finish.html', '_self')

    } else {
      function myFunction() {
        // Get the snackbar DIV
        var x = document.getElementById("snackbar");

        // Add the "show" class to DIV
        x.className = "show";

        // After 3 seconds, remove the show class from DIV
        setTimeout(function () { x.className = x.className.replace("show", ""); }, 3000);
        document.getElementById('snackbar').textContent = message
        document.getElementById('snackbar').style = 'color: white; background: red;'

      }
      myFunction()
    }

  })

})

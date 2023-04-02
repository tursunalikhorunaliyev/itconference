const api = 'http://35.242.182.188:8080/it-conference-kuva/auth/register'

const fam = document.getElementById('fam')
const tel = document.getElementById('tel')
const username = document.getElementById('ism')

let form = document.getElementById('form')

let userLogin = JSON.parse(localStorage.getItem('list')) ? JSON.parse(localStorage.getItem('list')) : []

function doFormat(x, pattern, mask) {
  var strippedValue = x.replace(/[^0-9]/g, "");
  var chars = strippedValue.split('');
  var count = 0;

  var formatted = '';
  for (var i = 0; i < pattern.length; i++) {
    const c = pattern[i];
    if (chars[count]) {
      if (/\*/.test(c)) {
        formatted += chars[count];
        count++;
      } else {
        formatted += c;
      }
    } else if (mask) {
      if (mask.split('')[i])
        formatted += mask.split('')[i];
    }
  }
  return formatted;
}

document.querySelectorAll('[data-mask]').forEach(function (e) {
  function format(elem) {
    const val = doFormat(elem.value, elem.getAttribute('data-format'));
    elem.value = doFormat(elem.value, elem.getAttribute('data-format'), elem.getAttribute('data-mask'));

    if (elem.createTextRange) {
      var range = elem.createTextRange();
      range.move('character', val.length);
      range.select();
    } else if (elem.selectionStart) {
      elem.focus();
      elem.setSelectionRange(val.length, val.length);
    }
  }
  e.addEventListener('keyup', function () {
    format(e);
  });
  e.addEventListener('keydown', function () {
    format(e);
  });
  format(e)
});



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
    console.log(item);

    let message = item.message
    let status = item.status


    if (status == true) {
      let cardId = item.data.cardID
      window.open('finish.html', '_self')
      localStorage.clear()
      userLogin.push({ "render": cardId })
      localStorage.setItem('list', JSON.stringify(userLogin))

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

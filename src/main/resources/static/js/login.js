
      let userLogin = JSON.parse(localStorage.getItem("list"))
        ? JSON.parse(localStorage.getItem("list"))
        : [];

      const phoneMaskSelector = ".js-phone-input";
      const phoneMaskInputs = document.querySelectorAll(phoneMaskSelector);

      const masksOptions = {
        phone: {
          mask: "(00) 000-00-00",
        },
      };

      for (const item of phoneMaskInputs) {
        new IMask(item, masksOptions.phone);
      }

      document.getElementById("form").addEventListener("submit", (i) => {
        i.preventDefault();
        let telN = document.getElementById("tel").value;
        let formdata = new FormData();

        formdata.append("phone", telN);
        fetch("http://35.242.182.188:8080/it-conference-kuva/auth/login", {
          method: "POST",
          body: formdata,
        })
          .then((data, i) => {
            console.log(data);
            return data.json();
          })
          .then((user) => {
            console.log(user);
            let cardUserStatus = user.status;
            let cardUserMessage = user.message;

            if (cardUserStatus == true) {
              let cardUserId = user.data.generated.cardID;
              window.open("kirish.html", "_self");
              userLogin.push({
                cardUserID: cardUserId,
              });
              localStorage.setItem("list", JSON.stringify(userLogin));
            } else {
              function myFunction() {
                // Get the snackbar DIV
                var x = document.getElementById("snackbar");

                // Add the "show" class to DIV
                x.className = "show";

                // After 3 seconds, remove the show class from DIV
                setTimeout(function () {
                  x.className = x.className.replace("show", "");
                }, 3000);
                document.getElementById("snackbar").textContent =
                  cardUserMessage;
                document.getElementById("snackbar").style =
                  "color: white; background: red;";
              }
              myFunction();
            }
          });
      });

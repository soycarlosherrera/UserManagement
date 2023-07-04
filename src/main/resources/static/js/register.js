// Call the dataTables jQuery plugin
$(document).ready(function() {  //Se ejecuta el codigo en cuanto termina de cargar la pagina
   //on ready
});

async function registerUser(){ //async porque usamos await

    let data = {};
    data.name = document.getElementById('txtName').value;
    data.lastName = document.getElementById('txtLastName').value;
    data.email = document.getElementById('txtEmail').value;
    data.password = document.getElementById('txtPassword').value;

    let repeatPassword = document.getElementById('txtRepeatPassword').value;

    if(repeatPassword!=data.password){
        alert('Las contraseñas no coinciden');
        return;
    }

    const request = await fetch('api/users', { //url a la cual se hace la peticion --await para esperar el resultado del llamado
    method: 'POST',
    headers: {
          'Accept': 'application/json',         //Cabeceras que indican uso de json
          'Content-Type': 'application/json'
        },

        body: JSON.stringify(data) //Llama a la funcion JSON.stringify que toma un objeto javascript y lo convierte a un Json

    });
    alert("La cuenta fue creada con exito!");
    window.location.href = 'login.html'
}
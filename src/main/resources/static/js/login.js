// Call the dataTables jQuery plugin
$(document).ready(function() {  //Se ejecuta el codigo en cuanto termina de cargar la pagina
   //on ready
});

async function loginUser(){ //async porque usamos await

    let data = {};
    data.email = document.getElementById('txtEmail').value;
    data.password = document.getElementById('txtPassword').value;

    const request = await fetch('api/login', { //url a la cual se hace la peticion --await para esperar el resultado del llamado
    method: 'POST',
    headers: {
          'Accept': 'application/json',         //Cabeceras que indican uso de json
          'Content-Type': 'application/json'
        },

        body: JSON.stringify(data) //Llama a la funcion JSON.stringify que toma un objeto javascript y lo convierte a un Json

    });
    const response = await request.text();     //Convierte el resultado en json
    if (response != 'FAIL'){
        localStorage.token = response; //almaceno el token en el cliente
        localStorage.email = data.email;
        window.location.href = 'users.html'
    }else{
        alert ("Las credenciales son incorrectas, por favor intente nuevamente.");
    }
}
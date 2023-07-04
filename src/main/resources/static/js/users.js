// Call the dataTables jQuery plugin
$(document).ready(function() {  //Se ejecuta el codigo en cuanto termina de cargar la pagina
  loadUsers(); //Para cargar usuarios en la tabla
  $('#users').DataTable();      //Toma la tabla users y le da funcionalidades de la libreria dataTable de javasCript
  updateEmailUser();
});

function updateEmailUser(){
    document.getElementById('txt-email-user').outerHTML = localStorage.email;
}

async function loadUsers(){ //async porque usamos await

    const request = await fetch('api/users', { //url a la cual se hace la peticion --await para esperar el resultado del llamado
    method: 'GET',
    headers: getHeaders()

    });
    const users = await request.json();     //Convierte el resultado en json

    let listUserHtml = '';

    for (let user of users){

        let deleteButton = '<a href="#" onclick="deleteUser('+user.id+')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
        //El string dentro de comilla simple, debido a que contiene comillas dobles y no reconoce bien
        let phoneText = user.phoneNumber == null ? '---' : user.phoneNumber; //Esto es para hacer un if en una sola linea
        let userHtml = '<tr><td>'+user.id+'</td><td>'+user.name+' '+user.lastName+'</td><td>'
                        +user.email+'</td><td>'+phoneText
                        +'</td><td>'+deleteButton+'</td></tr>';
        listUserHtml += userHtml;
    }

    document.querySelector('#users tbody').outerHTML = listUserHtml;

}

function getHeaders(){
    return {
        'Accept': 'application/json',         //Cabeceras que indican uso de json
        'Content-Type': 'application/json',
        'Authorization': localStorage.token    //Envio el token de sesion
    };
}

 async function deleteUser(id){ //async porque usamos await

    if (!confirm('¿Desea eliminar este usuario?')){
        return;
    }

    const request = await fetch('api/users/'+id, { //url a la cual se hace la peticion --await para esperar el resultado del llamado
        method: 'DELETE',
        headers: getHeaders()
        });

        location.reload()
}
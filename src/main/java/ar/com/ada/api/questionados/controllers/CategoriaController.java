package ar.com.ada.api.questionados.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.questionados.entities.Categoria;
import ar.com.ada.api.questionados.models.request.CategoriaNuevaInfo;
import ar.com.ada.api.questionados.models.response.GenericResponse;
import ar.com.ada.api.questionados.services.CategoriaService;

@RestController
public class CategoriaController {

    @Autowired
    CategoriaService service;

    
    //GET /categorias
    @GetMapping("/categorias") //hacer el mapping
    public ResponseEntity<List<Categoria>> traerCategorias() { //return Response Entity
        return ResponseEntity.ok(service.traerCategorias()); //return entity con el valor esperado
    }

    //GET Categoría por Id
    @GetMapping("/categorias/{id}")
    public ResponseEntity<Categoria> traerCategoriaPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarCategoria(id));
    }

    @PostMapping(value = "/categorias")
    public ResponseEntity<?> crearCategoria(@RequestBody Categoria categoria) {
        GenericResponse r = new GenericResponse();

        if (service.crearCategoria(categoria)) {
            r.id = categoria.getCategoriaId();
            r.isOk = true;
            r.message = "Categoria creada con exito";
            return ResponseEntity.ok(r);
        } else {
            r.isOk = false;
            r.message = "Esta categoria ya esta creada";
            return ResponseEntity.badRequest().body(r);
        }

    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<GenericResponse> actualizarCategoria(@PathVariable Integer id, @RequestBody CategoriaNuevaInfo categoriaNuevaInfo){

        service.actualizarCategoria(id, categoriaNuevaInfo);
        //Categoria categoria = service.buscarCategoria(id);
        //categoria.setNombre(categoriaNuevaInfo.nombreNuevo);
        //categoria.setDescripcion(categoriaNuevaInfo.descripcionNueva);
        //service.guardar(categoria);

        GenericResponse r = new GenericResponse();
        r.isOk = true;
        //r.id = categoria.getCategoriaId();
        r.message = "La categoría fue actualizada con éxito";

        return ResponseEntity.ok(r);
        
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<GenericResponse> eliminarCategoria(@PathVariable Integer id){

        service.eliminarCategoria(id);

        GenericResponse r = new GenericResponse();
        r.isOk = true;
        r.message = "La categoría fue eliminada con éxito.";

        return ResponseEntity.ok(r);
    }

    
}
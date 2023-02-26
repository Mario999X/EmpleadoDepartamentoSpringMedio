package resa.mario.empleadodepartamentospring2.controller

import jakarta.validation.Valid
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import resa.mario.empleadodepartamentospring2.config.APIConfig
import resa.mario.empleadodepartamentospring2.models.Departamento
import resa.mario.empleadodepartamentospring2.services.departamento.DepartamentosServiceImpl

// IMPORTANTE, PARA SPRING SECURITY -> LOS METODOS DE LOS CONTROLADORES EN PUBLICO

@RestController
@RequestMapping(APIConfig.API_PATH + "/departamentos")
class DepartamentoController
@Autowired constructor(
    private val service: DepartamentosServiceImpl
) {
    @GetMapping("")
    suspend fun findAll(): ResponseEntity<List<Departamento>> {
        return ResponseEntity.ok(service.findAll().toList())
    }

    @GetMapping("id/{id}")
    suspend fun findById(@PathVariable id: String): ResponseEntity<Departamento> {
        try {
            val entity = service.findById(id.toLong())
            return ResponseEntity.ok(entity)

        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }

    }

    @PostMapping("create")
    suspend fun create(@Valid @RequestBody entity: Departamento): ResponseEntity<Departamento> {
        return ResponseEntity.ok(service.save(entity))
    }

    @PutMapping("update/{id}")
    suspend fun update(
        @PathVariable id: String,
        @Valid @RequestBody entity: Departamento
    ): ResponseEntity<Departamento> {
        try {
            return ResponseEntity.ok(service.update(id.toLong(), entity))
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    @DeleteMapping("delete/{id}")
    suspend fun delete(@PathVariable id: String): ResponseEntity<Departamento> {
        try {
            return ResponseEntity.ok(service.deleteById(id.toLong()))
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }
}
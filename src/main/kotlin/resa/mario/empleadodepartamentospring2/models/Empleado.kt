package resa.mario.empleadodepartamentospring2.models

import kotlinx.serialization.Serializable
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Serializable
@Table("EMPLEADOS")
data class Empleado(
    @Id
    val id: Long? = null,
    val name: String,
    val email: String,
    @Column("departamento_id")
    var departamentoId: Long? = null
) {
    override fun toString(): String {
        return "Empleado(id=$id, name='$name', email='$email', departamentoId=$departamentoId)"
    }
}

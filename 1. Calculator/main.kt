fun main() {
    println("Bienvenido a la calculadora en Kotlin 2025!")

    // Mostrar las opciones
    println("Selecciona una operación:")
    println("1. Sumar")
    println("2. Restar")
    println("3. Multiplicar")
    println("4. Dividir")

    // Leer la opción del usuario
    print("Ingresa el número de la operación que deseas realizar (1/2/3/4): ")
    val opcion = readLine()?.toIntOrNull()

    if (opcion == null || opcion !in 1..4) {
        println("Opción no válida. Por favor, ingresa una opción correcta.")
        return
    }

    // Solicitar los dos números
    print("Ingresa el primer número: ")
    val num1 = readLine()?.toDoubleOrNull()

    print("Ingresa el segundo número: ")
    val num2 = readLine()?.toDoubleOrNull()

    if (num1 == null || num2 == null) {
        println("Por favor, ingresa números válidos.")
        return
    }

    // Realizar la operación seleccionada
    when (opcion) {
        1 -> println("Resultado: ${num1 + num2}")
        2 -> println("Resultado: ${num1 - num2}")
        3 -> println("Resultado: ${num1 * num2}")
        4 -> {
            if (num2 == 0.0) {
                println("Error: no se puede dividir por cero.")
            } else {
                println("Resultado: ${num1 / num2}")
            }
        }
    }
}



# Realizar un programa que conste de una clase llamada Alumno
# que tenga como atributos el nombre y la nota del alumno.
# Definir los métodos para inicializar sus atributos,
# imprimirlos y mostrar un mensaje con el resultado de la nota
# y si ha aprobado o no.
class Alumno:
    def __init__(self, nombre: str, nota: float):
        self.nombre = nombre
        self.nota = nota

    def __str__(self):
        return self.nombre + ": " + str(self.nota) + (" aprobado" if self.aprobado else "suspenso")

    @property
    def aprobado(self):
        return self.nota >= 5


# Realizar un programa que tenga una clase Persona
# con las siguientes características.
# La clase tendrá como atributos el nombre y la edad de una persona.
# Implementar los métodos necesarios para inicializar los atributos,
# mostrar los datos e indicar si la persona es mayor de edad o no.
class Persona:
    def __init__(self, nombre: str, edad: int):
        self.nombre = nombre
        self.edad = edad

    def __str__(self):
        return self.nombre + ": " + str(self.edad) + (" mayor" if self.mayor else "menor")

    @property
    def mayor(self):
        return self.edad >= 18


# Desarrollar un programa que cargue los datos de un triángulo.
# Implementar una clase con los métodos para inicializar los atributos,
# imprimir el valor del lado con un tamaño mayor
# y el tipo de triángulo que es (equilátero, isósceles o escaleno).
class Triangulo:
    def __init__(self, a: float, b: float, c: float):
        self.a = a
        self.b = b
        self.c = c

    @property
    def tipo(self):
        return "equilatero" if (self.a == self.b and self.b == self.c) else "isosceles" if (self.a == self.b or self.a == self.c or self.b == self.c) else "escaleno"


# Realizar un programa en el cual se declaren dos valores enteros
# por teclado utilizando el método __init__.
# Calcular después la suma, resta, multiplicación y división.
# Utilizar un método para cada una e imprimir los resultados obtenidos.
# Llamar a la clase Calculadora.
class Calculadora:
    def __init__(self, a, b):
        self.a = a
        self.b = b

    def suma(self):
        return self.a + self.b

    def resta(self):
        return self.a - self.b

    def multiplica(self):
        return self.a * self.b

    def divide(self):
        return self.a / self.b


# Realizar una clase que administre una agenda.
# Se debe almacenar para cada contacto el nombre,
# el teléfono y el email.
# Además deberá mostrar un menú con las siguientes opciones:
class Agenda:
    def __init__(self):
        self._contactos = []

    def anadir(self, nombre, telefono, email):
        self._contactos.append(
            {"nombre": nombre, "telefono": telefono, "email": email})

    def listar(self):
        for contacto in self._contactos:
            print(self._contactos[contacto])

    def buscar(self, nombre):
        for contacto in self._contactos:
            if self._contactos[contacto].nombre == nombre:
                return self._contactos[contacto]

    def editar(self, nombre, telefono, email):
        contacto = self.buscar(nombre)
        contacto["telefono"] = telefono
        contacto["email"] = email

    def cerrar(self):
        self._contactos = []


# En un banco tienen clientes que pueden hacer depósitos y extracciones de dinero.
# El banco requiere también al final del día calcular la cantidad de dinero
# que se ha depositado.
# Se deberán crear dos clases, la clase cliente y la clase banco.
# La clase cliente tendrá los atributos nombre y cantidad
# y los métodos __init__, depositar, extraer, mostrar_total.
# La clase banco tendrá como atributos 3 objetos de la clase cliente
# y los métodos __init__, operar y deposito_total.
class Cliente:
    def __init__(self, nombre, cantidad):
        self.nombre = nombre
        self.cantidad = cantidad

    def depositar(self, cantidad):
        self.cantidad += cantidad

    def extraer(self, cantidad):
        self.cantidad -= cantidad

    def mostrar(self):
        print(self.cantidad)


class Banco:
    def __init__(self):
        self._clientes = []

    def operar(self, cliente):
        self._clientes.append(cliente)

    @property
    def deposito_total(self):
        return sum(map(lambda x: x.cantidad, self._clientes))


# Desarrollar un programa que conste de una clase padre Cuenta
# y dos subclases PlazoFijo y CajaAhorro.
# Definir los atributos titular y cantidad y un método
# para imprimir los datos en la clase Cuenta.
# La clase CajaAhorro tendrá un método para heredar los datos
# y uno para mostrar la información.
# La clase PlazoFijo tendrá dos atributos propios, plazo e interés.
# Tendrá un método para obtener el importe del interés
# (cantidad*interés/100) y otro método
# para mostrar la información, datos del titular plazo,
# interés y total de interés.
class Cuenta(base):
    def __init__(self, titular, cantidad):
        self.titular = titular
        self.cantidad = cantidad

    def imprimir(self):
        pass


class PlazoFijo(Cuenta):
    def __init__(self, titular, cantidad):
        super().__init__(titular, cantidad)


class CajaAhorro(Cuenta):
    def __init__(self, titular, cantidad, plazo, interes):
        super().__init__(titular, cantidad)
        self.plazo = plazo
        self.interes = interes


if __name__ == "__main__":
    c1 = Cliente("Hola Mundo", 5000)
    c2 = Cliente("Mundo", 6000)
    b = Banco()
    b.operar(c1)
    b.operar(c2)
    print(b.deposito_total)


# Realizar un programa que conste de una clase llamada Alumno
# que tenga como atributos el nombre y la nota del alumno.
# Definir los métodos para inicializar sus atributos,
# imprimirlos y mostrar un mensaje con el resultado de la nota
# y si ha aprobado o no.
class Alumno:
    def __init__(self, nombre: str, nota: float):
        self.nombre = nombre
        self.nota = nota

    def __str__(self):
        return self.nombre + ": " + str(self.nota) + (" aprobado" if self.aprobado else "suspenso")

    @property
    def aprobado(self):
        return self.nota >= 5


# Realizar un programa que tenga una clase Persona
# con las siguientes características.
# La clase tendrá como atributos el nombre y la edad de una persona.
# Implementar los métodos necesarios para inicializar los atributos,
# mostrar los datos e indicar si la persona es mayor de edad o no.
class Persona:
    def __init__(self, nombre: str, edad: int):
        self.nombre = nombre
        self.edad = edad

    def __str__(self):
        return self.nombre + ": " + str(self.edad) + (" mayor" if self.mayor else "menor")

    @property
    def mayor(self):
        return self.edad >= 18


# Desarrollar un programa que cargue los datos de un triángulo.
# Implementar una clase con los métodos para inicializar los atributos,
# imprimir el valor del lado con un tamaño mayor
# y el tipo de triángulo que es (equilátero, isósceles o escaleno).
class Triangulo:
    def __init__(self, a: float, b: float, c: float):
        self.a = a
        self.b = b
        self.c = c

    @property
    def tipo(self):
        return "equilatero" if (self.a == self.b and self.b == self.c) else "isosceles" if (self.a == self.b or self.a == self.c or self.b == self.c) else "escaleno"


# Realizar un programa en el cual se declaren dos valores enteros
# por teclado utilizando el método __init__.
# Calcular después la suma, resta, multiplicación y división.
# Utilizar un método para cada una e imprimir los resultados obtenidos.
# Llamar a la clase Calculadora.
class Calculadora:
    def __init__(self, a, b):
        self.a = a
        self.b = b

    def suma(self):
        return self.a + self.b

    def resta(self):
        return self.a - self.b

    def multiplica(self):
        return self.a * self.b

    def divide(self):
        return self.a / self.b


# Realizar una clase que administre una agenda.
# Se debe almacenar para cada contacto el nombre,
# el teléfono y el email.
# Además deberá mostrar un menú con las siguientes opciones:
class Agenda:
    def __init__(self):
        self._contactos = []

    def anadir(self, nombre, telefono, email):
        self._contactos.append(
            {"nombre": nombre, "telefono": telefono, "email": email})

    def listar(self):
        for contacto in self._contactos:
            print(self._contactos[contacto])

    def buscar(self, nombre):
        for contacto in self._contactos:
            if self._contactos[contacto].nombre == nombre:
                return self._contactos[contacto]

    def editar(self, nombre, telefono, email):
        contacto = self.buscar(nombre)
        contacto["telefono"] = telefono
        contacto["email"] = email

    def cerrar(self):
        self._contactos = []


# En un banco tienen clientes que pueden hacer depósitos y extracciones de dinero.
# El banco requiere también al final del día calcular la cantidad de dinero
# que se ha depositado.
# Se deberán crear dos clases, la clase cliente y la clase banco.
# La clase cliente tendrá los atributos nombre y cantidad
# y los métodos __init__, depositar, extraer, mostrar_total.
# La clase banco tendrá como atributos 3 objetos de la clase cliente
# y los métodos __init__, operar y deposito_total.
class Cliente:
    def __init__(self, nombre, cantidad):
        self.nombre = nombre
        self.cantidad = cantidad

    def depositar(self, cantidad):
        self.cantidad += cantidad

    def extraer(self, cantidad):
        self.cantidad -= cantidad

    def mostrar(self):
        print(self.cantidad)


class Banco:
    def __init__(self):
        self._clientes = []

    def operar(self, cliente):
        self._clientes.append(cliente)

    @property
    def deposito_total(self):
        return sum(map(lambda x: x.cantidad, self._clientes))


# Desarrollar un programa que conste de una clase padre Cuenta
# y dos subclases PlazoFijo y CajaAhorro.
# Definir los atributos titular y cantidad y un método
# para imprimir los datos en la clase Cuenta.
# La clase CajaAhorro tendrá un método para heredar los datos
# y uno para mostrar la información.
# La clase PlazoFijo tendrá dos atributos propios, plazo e interés.
# Tendrá un método para obtener el importe del interés
# (cantidad*interés/100) y otro método
# para mostrar la información, datos del titular plazo,
# interés y total de interés.
class Cuenta(base):
    def __init__(self, titular, cantidad):
        self.titular = titular
        self.cantidad = cantidad

    def imprimir(self):
        raise NotImplementedError()


class PlazoFijo(Cuenta):
    def __init__(self, titular, cantidad):
        super().__init__(titular, cantidad)

    def imprimir(self):
        print(self.titular + " : " + str(self.cantidad))


class CajaAhorro(Cuenta):
    def __init__(self, titular, cantidad, plazo, interes):
        super().__init__(titular, cantidad)
        self.plazo = plazo
        self.interes = interes

    @property
    def import_interes(self):
        return self.cantidad * self.interes / 100

    def imprimir(self):
        print(self.titular + " : " + str(self.cantidad) + " , " +
              str(self.interes) + " , " + str(self.import_interes))


if __name__ == "__main__":
    c1 = Cliente("Hola Mundo", 5000)
    c2 = Cliente("Mundo", 6000)
    b = Banco()
    b.operar(c1)
    b.operar(c2)
    print(b.deposito_total)

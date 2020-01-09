import random


class Jugador:
    def __init__(self, nombre, apellidos, edad, mano):
        self.nombre = nombre
        self.apellidos = apellidos
        self.edad = edad
        self.mano = mano

    def __str__(self):
        return "Nombre: " + self.nombre + "; Apellido: " + self.apellidos + "; Edad: " + str(self.edad) + "; Mano: " + self.mano


class Pais:
    def __init__(self, nombre):
        self.nombre = nombre
        self._jugadores = []

    def anadir_jugador(self, jugador: Jugador):
        self._jugadores.append(jugador)

    def __str__(self):
        return "Nombre: " + self.nombre + "\nJugadores: \n    " + "\n    ".join(map(str, self._jugadores))


def create() -> Pais:
    nombre = input("Introduzca el nombre de pais: ")
    pais = Pais(nombre)
    n = int(input("Introduzca el numero de jugadores: "))
    for i in range(n):
        nombre = input("Introduzca el nombre de jugador: ")
        apellido = input("Introduzca el apellido de jugador: ")
        edad = int(input("Introduzca el edad de jugador: "))
        mano = input("Introduzca el costumbre de jugador: ")
        pais.anadir_jugador(Jugador(nombre, apellido, edad, mano))
    return pais


class Encuentro:
    def __init__(self, paises, resultado):
        self.paises = paises
        self.resultado = resultado


class Torneo:
    def __init__(self):
        self.participantes = []
        self.grupos = []

    def dar_de_alta(self, pais: Pais):
        self.participantes.append(pais)

    def dar_de_baja(self, nombre: str):
        tmp = [p for p in self.participantes if p.nombre != nombre]
        self.participantes = tmp

    def cargar(self, fichero: str):
        with open(fichero) as f:
            lines = f.readlines()
            for line in lines:
                params = line.split("|")
                pais = Pais(params[0])
                params = params[1:]
                for param in params:
                    jugador = param.split()
                    pais.anadir_jugador(
                        Jugador(jugador[0], jugador[1], int(jugador[2]), jugador[3]))
                self.participantes.append(pais)

    def mostrar(self):
        for participante in self.participantes:
            print(participante)
            print()

    def mostrar_resultado(self):
        eliminatoria = []
        no_eliminatoria = []
        for index, grupo in enumerate(self.grupos):
            print("Primera Ronda: Grupo " + chr(ord('A') + index))
            print("Nación".ljust(20) + "Jugados".ljust(10) + "Ganados".ljust(10) +
                  "Perdidos".ljust(10) + "Partidos G-P".ljust(10))
            win1vs2 = random.randrange(4)
            win1vs3 = random.randrange(4)
            win2vs3 = random.randrange(4)
            g0 = (1 if win1vs2 >= 2 else 0) + (1 if win1vs3 >= 2 else 0)
            g1 = (0 if win1vs2 >= 2 else 1) + (1 if win2vs3 >= 2 else 0)
            g2 = (0 if win1vs3 >= 2 else 1) + (0 if win2vs3 >= 2 else 1)
            partidos0 = (win1vs2 + win1vs3, 6 - (win1vs2 + win1vs3))
            partidos1 = ((3 - win1vs2) + win2vs3, win1vs2 + (3-win2vs3))
            partidos2 = (6 - (win1vs3 + win2vs3), win1vs3 + win2vs3)
            print(grupo[0].nombre.ljust(20) + "2".ljust(10) + str(g0).ljust(10) +
                  str(2-g0).ljust(10) + str(partidos0))
            print(grupo[1].nombre .ljust(20) + "2".ljust(10) + str(g1).ljust(10) +
                  str(2-g1).ljust(10) + str(partidos1))
            print(grupo[2].nombre .ljust(20) + "2".ljust(10) + str(g2).ljust(10) +
                  str(2-g2).ljust(10) + str(partidos2))
            if partidos0[0] > partidos1[0] and partidos0[0] > partidos2[0]:
                eliminatoria.append(grupo[0])
                no_eliminatoria.append((grupo[1], partidos1))
                no_eliminatoria.append((grupo[2], partidos2))
            elif partidos1[0] > partidos0[0] and partidos1[0] > partidos2[0]:
                eliminatoria.append(grupo[1])
                no_eliminatoria.append((grupo[0], partidos0))
                no_eliminatoria.append((grupo[2], partidos2))
            else:
                eliminatoria.append(grupo[2])
                no_eliminatoria.append((grupo[0], partidos0))
                no_eliminatoria.append((grupo[1], partidos1))
        eliminatoria.extend(
            list(map(lambda x: x[0], sorted(no_eliminatoria, key=lambda x: x[1])[:2])))

        semifinales = []
        print("Cuartos de final")
        for i in range(0, len(eliminatoria), 2):
            puntuacion = random.randrange(4)
            if (puntuacion >= 2):
                semifinales.append(eliminatoria[i])
            else:
                semifinales.append(eliminatoria[i+1])
            print('\t\t'+eliminatoria[i].nombre.ljust(20) + str(puntuacion))
            print(
                '\t\t'+eliminatoria[i+1].nombre.ljust(20) + str(3 - puntuacion))
            print()

        finales = []
        print("Semifinales")
        for i in range(0, len(semifinales), 2):
            puntuacion = random.randrange(4)
            if (puntuacion >= 2):
                finales.append(semifinales[i])
            else:
                finales.append(semifinales[i+1])
            print('\t\t' + semifinales[i].nombre.ljust(20) + str(puntuacion))
            print(
                '\t\t' + semifinales[i+1].nombre.ljust(20) + str(3 - puntuacion))

        print("Final")
        puntuacion = random.randrange(4)
        print('\t\t' + finales[0].nombre.ljust(20) + str(puntuacion))
        print('\t\t' + finales[1].nombre.ljust(20) + str(3 - puntuacion))

    def crear_equipo(self,  pais1: Pais, pais2: Pais, pais3: Pais):
        self.grupos.append((pais1, pais2, pais3))
        self.participantes = [
            p for p in self.participantes if p != pais1 and p != pais2 and p != pais3]
        return chr(ord('A') + len(self.grupos) - 1)

    def crear_equipo_automatica(self):
        while self.participantes:
            self.crear_equipo(self.participantes[0],
                              self.participantes[1],
                              self.participantes[2])

    def crear_equipo_manual(self, pais1: str, pais2: str, pais3: str):
        self.crear_equipo(
            [p for p in self.participantes if p.nombre == pais1][0],
            [p for p in self.participantes if p.nombre == pais2][0],
            [p for p in self.participantes if p.nombre == pais3][0],
        )


if __name__ == "__main__":
    torneo = Torneo()
    options = {
        "a": ("Dar de alta", lambda: torneo.dar_de_alta(create())),
        "b": ("Dar de baja", lambda: torneo.dar_de_baja(input("Introduzca el nombre de pais: "))),
        "c": ("Cargar datos", lambda: torneo.cargar(input("Introduzca el nombre de archivo: "))),
        "d": ("Mostrar", torneo.mostrar),
        "e": ("Crear equipo manual", lambda: torneo.crear_equipo_manual(input("Introduzca el nombre del pais 1: "), input("Introduzca el nombre del pais 2: "), input("Introduzca el nombre del pais 3: "))),
        "f": ("Create equipo automaticamente", torneo.crear_equipo_automatica),
        "g": ("Mostrar el resultado", torneo.mostrar_resultado)
    }
    while True:
        for op in options:
            print(op + ") " + options[op][0])
        selected = input("Indica tu operation: ")
        if selected in options:
            options[selected][1]()
        else:
            print("operacion invalida")

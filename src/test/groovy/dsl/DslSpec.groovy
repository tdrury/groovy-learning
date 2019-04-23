package dsl

import spock.lang.Specification

class DslSpec extends Specification {

    def "trivial dsl"() {

        when:
        def bom = new BOM(
            name: "bom1",
            components: [
                    new Component( item: new Item(name: "res - 3.3K"), quantity: 1, refdes: "R1"),
                    new Component( item: new Item(name: "cap - 1u"), quantity: 1, refdes: "C1")
            ]
        )

        then:
        bom.name == "bom1"
        bom.components[0].item.name == "res - 3.3K"
        bom.components[0].quantity == 1
        bom.components[0].refdes == "R1"
        bom.components[1].item.name == "cap - 1u"
        bom.components[1].quantity == 1
        bom.components[1].refdes == "C1"
    }
}


class Item {
    String name
}

class Component {
    Item item
    BigDecimal quantity
    String refdes
}

class BOM {
    String name
    Component[] components
}
package com.github.bartcowski.gymkeeper.domain.user

import spock.lang.Specification
import spock.lang.Subject

class UserTest extends Specification {

    @Subject
    User user = new User(
            new UserId(1),
            new Username("username"),
            UserGender.MALE,
            new UserAge(30),
            new UserWeight(96),
            new UserHeight(185)
    )

    def "should properly calculate BMI"() {
        when:
        def BMI = user.calculateBMI()

        then:
        BMI == 28.05d
    }

    def "should properly calculate FFMI"() {
        given:
        def bodyFatPercentage = new BodyFatPercentage(20.0)

        when:
        def FFMI = user.calculateFFMI(bodyFatPercentage)

        then:
        FFMI == 22.13d
    }

}

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class TVTest {

    @Test
    fun constructorTest() {
        val tv = TV()
        assert(tv.n_channels == 5, { "Default number of channels is wrong." })
        assert(!tv.isOn, { "TV is on after construction!" })
        assert(tv.channel == null, { "Channel is selected after construction!" })
    }

    @Test
    fun switchOnTest() {
        val tv = TV()
        tv.isOn = true
        assert(tv.isOn, { "TV is not on." })
        assert(tv.channel == 1, { "Channel is not 1 after power on." })
    }

    @Test
    fun switchOffTest() {
        val tv = TV()
        tv.isOn = true
        tv.isOn = false
        assert(tv.channel == null, { "Channel selected after power off." })
    }

    @Test
    fun channelUpTest1() {
        val tv = TV()
        tv.isOn = true
        tv.channelUp()
        assert(tv.channel == 2, { "Channel wrong after first up." })
    }

    @Test
    fun channelUpTest2() {
        val tv = TV()
        tv.isOn = true
        (1..tv.n_channels-1).forEach { tv.channelUp() }
        assert(tv.channel == tv.n_channels, { "Bad channel after ${tv.n_channels} channelUps" })
        tv.channelUp()
        assert(tv.channel == 1, { "Channel should be 1 after ${tv.n_channels}" })
    }
}
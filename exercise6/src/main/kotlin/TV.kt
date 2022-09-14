class TV {
    var n_channels: Int = 5

    var isOn: Boolean = false
        get() = field
        set(value) {
            field = value
            channel = if (value) 1 else null
        }

    var channel: Int? = null

    fun channelUp() {
        if (this.channel == null) {
            return
        }

        this.channel = this.channel?.plus(1)

        if (this.channel!! > this.n_channels) {
            this.channel = 1
        }
    }
}
/**
 * This class is responsible for handling the Sockets.
 * The sockets use an old version of Autobahn.js in order to run, as the server
 * runs on WAMPv1, and the new versions of Autobahn.js use WAMPv2.
 *
 * This page holds the documentation information for this version of Autobahn.js
 * http://autobahn.ws/js/reference_wampv1.html
 *
 * @returns {SocketController.SocketAnonym$0}
 */

var SocketController = function () {
    /**
     * This variable holds the Autobahn Websocket connection information.
     */
    this.conn = null;

    this.client = null;

    /**
     * This object holds all the various loose variables
     */
    this.Data = {
        connected: false
    }

    /**
     * This return function allows us to be able to see the functions and variables
     * that we define with this.
     * It also lets us remotely call the classes functions.
     * I wouldnt see this as defining the variables/functions as public as its
     * a little weird.
     *
     * If you wanted to string the functions, like this:
     * Socket.connect().Subscribe().Broascast()
     * You could easily do that by storing this class in its own return variable,
     * then having all children functions return this class.
     */
    return {
        conn: this.conn,
        client: this.client,
        Data: this.Data,
        connect: this.connect,
        onOpen: this.onOpen,
        onClose: this.onClose,
        Close: this.Close,
        Subscribe: this.Subscribe,
        unSubscribe: this.unSubscribe,
        getAppURL: this.getAppURL,
        getTopicURL: this.getTopicURL,
        onBroadcast: this.onBroadcast,
        onSystemBroadcast: this.onSystemBroadcast,
        Broadcast: this.Broadcast,
        onMessage: this.onMessage,
        updateName: this.updateName,
        saveChannel: this.saveChannel,
        restoreChannels: this.restoreChannels,
        removeChannel: this.removeChannel
    }
}

/**
 * This method connects to the server.
 *
 * @returns {undefined}
 */
SocketController.prototype.connect = function () {
    /**
     * Connect to the server, and pass through the callback variables
     *
     * ab is Autobahn.js, refer to its documentation for more information.
     * By default the functions we pass through have the ab session as their context,
     * but we want the Class to be the context (because we can easily do this.conn)
     * so we bind the classes context to the functions we pass through.
     */

    this.conn = new SockJS('http://localhost:8080/hello');
    this.client = Stomp.over(this.conn);
    //this.client.connect({}, this.onOpen.bind(this));
    this.client.connect("guest", "guest", this.onOpen.bind(this), {});

    /**
     * This function tells the page to update its DOM to show the connecting thing
     */
    Scene.onConnectionStart();
}

/**
 * This method is a callback which is ran when the client manages to successfully
 * connect to the server.
 *
 * It also subscribes the user to some example topics
 *
 * @returns {undefined}
 */
SocketController.prototype.onOpen = function () {
    /**
     * Update our variable so we can record the users connected status
     */
    this.Data.connected = true;

    /**
     * We connect to all previously saved channels.
     * Subscriptions are wiped after a disconnect, and so we must reconnect
     * to all the clients subscribed subscriptions.
     *
     */
    if (!this.restoreChannels()) {
        /**
         * If we have not restored any channels from the local storage, then that
         * is likely because we have not got any saved, so we will subscribe the
         * client to some example topics
         */
        this.Subscribe(['#Cats', '#Dogs', '#ApacheHelicopters']);
    }

    /**
     * Update the DOM to tell the user they are connected, and prompt for their
     * display name
     */
    Scene.onConnected();
}

/**
 * This method is a callback which is ran when the connection to the server is
 * closed.
 *
 * @param {type} e
 * @returns {undefined}
 */
SocketController.prototype.onClose = function () {
    /**
     * Update our connected variable to know of the connected status
     */
    this.Data.connected = false;

    /**
     * Update the DOM to tell the user that they have disconnected, and prompt
     * them to reconnect
     */
    Scene.onDisconnected();
}

/**
 * This method closes the current connection
 *
 * @returns {undefined}
 */
SocketController.prototype.Close = function () {
    this.conn.close();
}

/**
 * This method subscribes the client to a topic.
 * You can pass the topics name as a string, or a series of topics as an array.
 * for example:
 * this.Subscribe('#CatFacts');
 * this.Subscribe(['#CatFacts', '#ZombieApocalypse']);
 *
 * @param {Array|String} topic
 * @returns {undefined}
 */
SocketController.prototype.Subscribe = function (topic) {
    /**
     * This statement converts a string to a single index array, to keep the rest
     * of the code uniform
     */
    if (typeof topic === 'string') {
        topic = [topic];
    }

    /**
     * Make sure all the topics are unique
     */
    topic = uniq(topic);

    /**
     * Loop through all the entered topics, then if they have not subscribed
     * to that topic, then we will subscribe them to it.
     */
    topic.forEach(function (e) {
        if (!ViewModel.isSubscribed(e)) {
            this.client.subscribe(this.getTopicURL(e), this.onBroadcast.bind(this));
            ViewModel.Subscribe(e);
            this.saveChannel(e);
        }
    }.bind(this));

    this.client.subscribe("/topic/system", this.onSystemBroadcast.bind(this));

    /**
     * Load previous messages on the subscribed channels that occured in the past
     * 24 hours.
     */
    loadJSON('/SimpleFacts/ajax.retrieveMessages.php?topics=' + topic.join(',').replace(/#/g, '@'), function (obj) {
        obj.forEach(function (e) {
            ViewModel.addMessage({
                fingerprint: e.fingerprint,
                name: e.name,
                value: e.msg,
                time: e.time
            });
        });
    });
}

/**
 * This method unsubscribes the client from a topic.
 * You can pass the topics name as a string, or a series of topics as an array.
 * for example:
 * this.unSubscribe('#CatFacts');
 * this.unSubscribe(['#CatFacts', '#ZombieApocalypse']);
 *
 * @param {Array|String} topic
 * @returns {undefined}
 */
SocketController.prototype.unSubscribe = function (topic) {
    /**
     * This statement converts a string to a single index array, to keep the rest
     * of the code uniform
     */
    if (typeof topic === 'string') {
        topic = [topic];
    }

    /**
     * Make sure all the topics are unique
     */
    topic = uniq(topic);

    /**
     * Loop through all the entered topics, then if they have not subscribed
     * to that topic, then we will subscribe them to it.
     */
    topic.forEach(function (e) {
        if (ViewModel.isSubscribed(e)) {
            this.client.unsubscribe(e);
            ViewModel.unSubscribe(e);
            this.removeChannel(e);
        }
    }.bind(this));
}

/**
 * This method simply publishes whatever data you want to a specific topic
 *
 * @param {String} topic
 * @param {Array|String|Object} data
 * @returns {undefined}
 */
SocketController.prototype.Broadcast = function (topic, data) {
    this.client.send(this.getAppURL(topic), {}, JSON.stringify(data));
}

SocketController.prototype.getAppURL = function (topic) {
    return "/app/chat.message." + topic.replace('#', '').toLowerCase();
}

SocketController.prototype.getTopicURL = function (topic) {
    return "/topic/chat.message." + topic.replace('#', '').toLowerCase();
}

/**
 * This method is a callback which is ran once the client recieves a broadcast
 * message from the server.
 *
 * This method tells the client what to do with the message they have recieved
 *
 * @param {Array|String|Object} data
 * @returns {undefined}
 */
SocketController.prototype.onBroadcast = function (json) {
    var data = JSON.parse(json.body);
    ViewModel.addMessage({
        fingerprint: data.fingerprint,
        username: data.username,
        message: data.message,
        time: data.time
    });
}

SocketController.prototype.onSystemBroadcast = function (json) {
    var data = JSON.parse(json.body);
    if (data.type.toUpperCase() === "CONNECTEDUSERS") {
        var payload = JSON.parse(data.payload);

        Object.keys(payload).forEach(function (e) {
            var User = {
                ID: e,
                Name: payload[e]
            }
            console.log(User);
            if (User.Name.length === 0) {
                ViewModel.removeUser(User);
            } else {
                ViewModel.addUser(User);
            }
        }.bind(this));
    }
}

/**
 * This method is ran when the client updates their display name.
 * It sends a message to the server telling it.
 * @returns {undefined}
 */
SocketController.prototype.updateName = function () {
    this.client.send('/app/system.name', {}, JSON.stringify({
        name: Scene.Data.User.Name
    }))
}

/**
 * Remove a topic from the list of saved channels
 * @param {String} topic
 * @returns {undefined}
 */
SocketController.prototype.removeChannel = function (topic) {
    var SubscribedChannels = [];

    var ChannelStorage = window.localStorage.getItem('SubscribedChannels');
    if (ChannelStorage) {
        SubscribedChannels = JSON.parse(ChannelStorage);
        var topicItem = SubscribedChannels.indexOf(topic);
        if (topicItem >= 0) {
            SubscribedChannels.splice(topicItem, 1);

            window.localStorage.setItem('SubscribedChannels', JSON.stringify(SubscribedChannels));
        }
    }
}

/**
 * Save a new channel to the local storage
 * @param {String} topic
 * @returns {undefined}
 */
SocketController.prototype.saveChannel = function (topic) {
    var SubscribedChannels = [];

    /**
     * Retrieve saved channels
     */
    var ChannelStorage = window.localStorage.getItem('SubscribedChannels');
    if (ChannelStorage) {
        SubscribedChannels = JSON.parse(ChannelStorage);
    }

    /**
     * Update the storage, if the element does not already exist in the array
     */
    if (SubscribedChannels.indexOf(topic) === -1) {
        SubscribedChannels.push(topic);
        window.localStorage.setItem('SubscribedChannels', JSON.stringify(SubscribedChannels));
    }
}

/**
 * Connect to all channels that are stored in the local storage.
 * @returns {Boolean} - Returns true if we restored any channels
 */
SocketController.prototype.restoreChannels = function () {
    var ChannelStorage = window.localStorage.getItem('SubscribedChannels');

    if (ChannelStorage) {
        this.Subscribe(JSON.parse(ChannelStorage));
        this.client.subscribe("/topic/system", this.onSystemBroadcast.bind(this));

        return true;
    }

    return false;
}

/**
 * Because of how I created this function/object prototype, we need to
 * instantiate it.
 * So it is instantiated as Socket.
 * @type SocketController
 */
var Socket = new SocketController;
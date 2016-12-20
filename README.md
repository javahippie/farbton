# farbton

This is a Clojure library which is wrapping the Philips Hue REST API. It enables you to interact with the lights in your home.

## Usage
### Discover the bridge

This is making use of the Philips UPnP tool and requires an internet connection for now. 
It returns the ID and IP Address of your bridge.

```clojure
(discover-bridge)

> {:id "001788fffe29d301", 
   :internalipaddress "192.168.2.103"}
```

### Request Username

Before making any calls to the API you need to ask the bridge for a username. Initially you can achieve this with the following call. Parameters are the bridge information from the `(discover-bridge)` call and an identifier for your device.

```clojure
(request-username bridge 
                  device-name)


> {:success {:username "DgWsHMp0Ui6WwmcyXSZundZ9ho4iDgj8JEJGHmX8"}}
```
Before making this call, you have to ask the user to press the link button, else it will return an error:

```clojure
> {:error {:type 101, 
           :address "", 
           :description "link button not pressed"}}
```

### Request system information

Enables you to query information about your Hue system, will generate a whole lot of data.

```clojure
(request-stats username 
               bridge)

>[:lights {:1 {:state {:on true, ...}}}, 
          {:2 {...}},
          ...
  :groups {:1 {name "Living Room", 
               :lights ["1", "2"]
               :type "Room"
               ...}}
  ...] 
```

### Request overall light information

Lets you query overall information about all installed Philips Hue lights

```clojure
(request-lights username 
                bridge)

> {:1 {:state {:on true, 
               :bri 254, 
               :ct 366, 
               :alert "none", 
               :colormode "ct", 
               :reachable true}, 
       :type "Color temperature light", 
       :name "Stehlampe", 
       :modelid "LTW001", 
       :manufacturername "Ceiling Lamp", 
       :uniqueid "00:27:12:31:10:38:7d:22-0a", 
       :swversion "5.50.1.19085"}, 
   :2 {:state {:on ... }}}
```

### Request detailed light information

Lets you query detailed information about one Phlips Hue light

```clojure
(request-light username 
               bridge 
               "1")

> {:state {:on true, 
           :bri 254, 
           :ct 366, 
           :alert "none", 
           :colormode "ct", 
           :reachable true}, 
   :type "Color temperature light", 
   :name "Ceiling Lamp", 
   :modelid "LTW001", 
   :manufacturername "Philips", 
   :uniqueid "00:27:12:31:10:38:7d:22-0a", 
   :swversion "5.50.1.19085"}
```

### Change the state of a light

This enables you to control the hue lights. Unfortunately, right now I only own Philips Hue Ambience lights, so I can not yet test everything in the full color spectrum. This is a very basic method call, it will just forward the properties directly to the API. Creating more simple wrappers for color selection is planned

```clojure
(change-light-state username 
                    bridge 
                    "1" 
                    {:on true 
                     :ct 153}) ;;turns on the lamp in a cold color

> ({:success {:/lights/1/state/on true}} 
   {:success {:/lights/1/state/ct 153}})
```

### Request 

## License
Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

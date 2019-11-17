# Coana
[![Build][travis-shield]][travis]
[![Coverage][codecov-shield]][codecov]

_Coroutine Analytics_ `MetaData` elements

## Get started

### `CoanaScope` and `CoanaContext`

    val coanaScope = CoanaScope("scope")
    val coanaContext = CoanaContext("context")

### `CoanaPropertyKey`

    sealed class PropertyKey<Value> : CoanaPropertyKey<Value> {
        object ApiVersion : PropertyKey<Double>
        object AppVersion : PropertyKey<Long>
        object Dependency : PropertyKey<Long>
    }

### `CoanaProperty`

    val coanaDouble = CoanaProperty(ApiVersion, 1.2)
    val coanaLong = CoanaProperty(AppVersion, 3)
    val coanaString = CoanaProperty(Dependency, "4.5.6")

### `Coana`

    assert(coana.scope == "scope")
    assert(coana.context == "context")
    assert(coana.doubleProperties[ApiVersion] == 1.2)
    assert(coana.longProperties[ApiVersion] == 3)
    assert(coana.stringProperties[Dependency] == "4.5.6")

### Coroutine _DSL_

    val coana = withCoanaScope("scope") {
        withCoanaContext("context) {
            withCoanaProperty(ApiVersion, 1.2) {
                withContext(coanaLong + coanaString) {
                    coana
                }
            }
        }
    }


## MetaData

    object StringKey : Key<String>

    val meta = MetaData(StringKey, "stringValue")

    val data = withContext(meta) {
        coroutineContext[StringKey]
    }

    assert(meta == data)


## License

    Copyright (C) 2019 Christian Schmitz

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


  [travis-shield]: https://travis-ci.com/tynn-xyz/Coana.svg
  [travis]: https://travis-ci.com/tynn-xyz/Coana
  [codecov-shield]: https://codecov.io/gh/tynn-xyz/Coana/badge.svg
  [codecov]: https://codecov.io/gh/tynn-xyz/Coana

# CoAna
[![Build][travis-shield]][travis]
[![Coverage][codecov-shield]][codecov]

_Coroutine Analytics_ `MetaData` elements

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


  [travis-shield]: https://travis-ci.com/tynn-xyz/CoAna.svg
  [travis]: https://travis-ci.com/tynn-xyz/CoAna
  [codecov-shield]: https://codecov.io/gh/tynn-xyz/CoAna/badge.svg
  [codecov]: https://codecov.io/gh/tynn-xyz/CoAna

## Motivation

Let's be honest, create a RecyclerView, a SwipeRefreshLayout, display a message when there's no data to show, etc. all the time it's not funny. I thought to myself if I could do it easily? And ExtendedRecyclerView was born.


## Usage

#### Dependencies

```groovy
compile 'pt.hugofernandes:extendedrecyclerview:0.1.1'
```

#### XML

Replace the regular RecyclerView and SwipeRefreshLayout with ExtendedRecyclerView

```xml
<pt.hugofernandes.ExtendedRecyclerView
	android:layout_width="match_parent"
	android:layout_height="match_parent"
	app:empty_message="Nothing to show"/>
```
          
#### JAVA

```java
extendedRecyclerView(itemsAdapter);
extendedRecyclerView.setOnRefreshListener(this);
extendedRecyclerView.setRefreshEnabled(true || false);
```

## License

 Copyright 2015 Hugo Fernandes
 
 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License. You may obtain a copy of
 the License at
  
 http://www.apache.org/licenses/LICENSE-2.0
  
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 License for the specific language governing permissions and limitations under
 the License.
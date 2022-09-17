```
MVVM arch

- View                  # Only User Interface, Can dispatch clicks
- ViewModel             # Gets Data from Repositrory for View. has LiveData
- Repository            # 
  - Model               # Get Data from Database
    - DB
  - Remote Data Source
    - Webservice
```
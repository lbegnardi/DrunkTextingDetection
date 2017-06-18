# DrunkTextingDetection
Android application using an SVM to tell if the user is drunk based on how he/she writes on the device.

I presented it as my Bachelor's degree thesis almost 2 years ago, so it will probably not be very updated in terms of Android's most recent features and classes.
Also, the main aspect of the project was its machine learning one (which was realized in Pythin with the scikit-learn library), so forgive me for the really essential UI.

The first version was client-server, with the server implemented again in Python using the Django framework, so to keep the implementation of the ML part as simple as possible.
Then I decided to exploit the mobile OpenCV libraries, making the client able to classify new instances by itself. It does work, but unfortunately the app will now occupy 40MB or more on the device.

To collect the dataset, I basically had 180 people (90 drunk and 90 sober) answer some random questions writing on my phone, while keeping track of:
  - total time
  - total number of deleted characters
  - total number of autocorrections performed by the device
  - total number of completions (both chosen by the user and performed automatically by the device)
  - characters/second
  - delete/second
  - autocorrections/second
  - completions/second
  - a feedback given by the user on his/her condition

Through this approach I ended up with a pretty decent classifier, reaching a precision on the test set (60 samples out of 180) of 95% when using the user's feedback as a feature, of 81% otherwise.
Using a Natural Language Processing approach would probably be more robust and precise, but it would require a lot more data to get to such a high level of precision and, of course, it would be completely language-dependant.

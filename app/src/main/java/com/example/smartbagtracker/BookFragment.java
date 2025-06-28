package com.example.smartbagtracker;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;

import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

public class BookFragment extends Fragment {

    private Button btnAddBook;
    private boolean isAddMode = true; // true = "Add Book", false = "Scan Book"
    private LinearLayout subjectContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);

        // Initialize views
        btnAddBook = view.findViewById(R.id.btnAddBook);
        subjectContainer = view.findViewById(R.id.subjectContainer);

        // Set click listener
        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAddMode) {
                    // First click: Change to "Scan Book"
                    changeToScanMode();
                } else {
                    // Second click: Show popup dialog
                    showSubjectDialog(null, -1); // null for new subject
                }
            }
        });

        return view;
    }

    private void changeToScanMode() {
        // Change button to Scan Book mode
        btnAddBook.setText("Scan Book");
        btnAddBook.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_scan, 0, 0, 0);
        isAddMode = false;
    }

    private void showSubjectDialog(String existingSubject, int editIndex) {
        // Create dialog
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_enter_subject);

        // Make dialog background transparent for rounded corners
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        // Get dialog views
        EditText etSubject = dialog.findViewById(R.id.etSubject);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnAdd = dialog.findViewById(R.id.btnAdd);

        // If editing, populate existing subject and change button text
        if (existingSubject != null) {
            etSubject.setText(existingSubject);
            btnAdd.setText("Update");
        }

        // Cancel button click
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Add/Update button click
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = etSubject.getText().toString().trim();

                if (!subject.isEmpty()) {
                    if (existingSubject != null) {
                        // Update existing subject
                        updateSubjectInList(subject, editIndex);
                    } else {
                        // Add new subject
                        addSubjectToList(subject);
                    }
                    dialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "Please enter a subject", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    // Create styled edit button with vector drawable
    private ImageButton createEditButton() {
        ImageButton btnEdit = new ImageButton(getActivity());

        // Set the vector drawable icon
        btnEdit.setImageResource(R.drawable.ic_edit);

        // Style the button
        btnEdit.setBackgroundColor(Color.parseColor("#E8F5E8")); // Light green background
        btnEdit.setPadding(16, 16, 16, 16);
        btnEdit.setScaleType(ImageButton.ScaleType.FIT_CENTER);

        // Apply color filter to make icon more visible
        btnEdit.getDrawable().setColorFilter(Color.parseColor("#2E7D32"), PorterDuff.Mode.SRC_IN);

        LinearLayout.LayoutParams editParams = new LinearLayout.LayoutParams(
                120, // Fixed width
                80   // Fixed height
        );
        editParams.setMargins(8, 8, 4, 8);
        btnEdit.setLayoutParams(editParams);

        // Add elevation for material design look
        btnEdit.setElevation(4f);

        return btnEdit;
    }

    // Create styled delete button with vector drawable
    private ImageButton createDeleteButton() {
        ImageButton btnDelete = new ImageButton(getActivity());

        // Set the vector drawable icon
        btnDelete.setImageResource(R.drawable.ic_delete);

        // Style the button
        btnDelete.setBackgroundColor(Color.parseColor("#FFEBEE")); // Light red background
        btnDelete.setPadding(16, 16, 16, 16);
        btnDelete.setScaleType(ImageButton.ScaleType.FIT_CENTER);

        // Apply color filter to make icon more visible
        btnDelete.getDrawable().setColorFilter(Color.parseColor("#C62828"), PorterDuff.Mode.SRC_IN);

        LinearLayout.LayoutParams deleteParams = new LinearLayout.LayoutParams(
                120, // Fixed width
                80   // Fixed height
        );
        deleteParams.setMargins(4, 8, 8, 8);
        btnDelete.setLayoutParams(deleteParams);

        // Add elevation for material design look
        btnDelete.setElevation(4f);

        return btnDelete;
    }

    private void addSubjectToList(String subject) {
        // Create a horizontal LinearLayout for subject row
        LinearLayout subjectRow = new LinearLayout(getActivity());
        subjectRow.setOrientation(LinearLayout.HORIZONTAL);
        subjectRow.setPadding(16, 12, 16, 12);
        subjectRow.setBackgroundColor(Color.parseColor("#FAFAFA")); // Light gray background
        subjectRow.setElevation(2f); // Add subtle elevation

        // Set layout parameters for the row
        LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        rowParams.setMargins(8, 6, 8, 6);
        subjectRow.setLayoutParams(rowParams);

        // Create TextView for subject
        TextView subjectView = new TextView(getActivity());
        subjectView.setText("📚 " + subject);
        subjectView.setTextSize(16);
        subjectView.setTextColor(Color.parseColor("#212121"));
        subjectView.setPadding(0, 12, 0, 12);

        // Set layout parameters for TextView to take up available space
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f // weight = 1 to take remaining space
        );
        subjectView.setLayoutParams(textParams);

        // Create Edit and Delete buttons using helper methods
        ImageButton btnEdit = createEditButton();
        ImageButton btnDelete = createDeleteButton();

        // Set click listeners
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSubject(subject, subjectContainer.indexOfChild(subjectRow));
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSubject(subjectRow, subject);
            }
        });

        // Add views to row
        subjectRow.addView(subjectView);
        subjectRow.addView(btnEdit);
        subjectRow.addView(btnDelete);

        // Add row to container
        if (subjectContainer != null) {
            subjectContainer.addView(subjectRow);
        }

        Toast.makeText(getActivity(), "Subject added: " + subject, Toast.LENGTH_SHORT).show();
    }

    private void editSubject(String currentSubject, int index) {
        showSubjectDialog(currentSubject, index);
    }

    private void updateSubjectInList(String newSubject, int index) {
        if (subjectContainer != null && index >= 0 && index < subjectContainer.getChildCount()) {
            LinearLayout subjectRow = (LinearLayout) subjectContainer.getChildAt(index);
            TextView subjectView = (TextView) subjectRow.getChildAt(0); // First child is TextView
            subjectView.setText("📚 " + newSubject);

            // Update edit button click listener with new subject
            ImageButton btnEdit = (ImageButton) subjectRow.getChildAt(1);
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editSubject(newSubject, index);
                }
            });

            Toast.makeText(getActivity(), "Subject updated: " + newSubject, Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteSubject(LinearLayout subjectRow, String subject) {
        // Show confirmation dialog
        new AlertDialog.Builder(getActivity())
                .setTitle("Delete Subject")
                .setMessage("Are you sure you want to delete \"" + subject + "\"?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    if (subjectContainer != null) {
                        subjectContainer.removeView(subjectRow);
                        Toast.makeText(getActivity(), "Subject deleted: " + subject, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
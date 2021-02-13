package com.arne.mvvmexampleextension;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
public class NoteAdapter extends ListAdapter<NoteView, NoteAdapter.NoteHolder> {

    public static final DiffUtil.ItemCallback<NoteView> DIFF_CALLBACK = new DiffUtil.ItemCallback<NoteView>() {
        @Override
        public boolean areItemsTheSame(@NonNull NoteView oldItem, @NonNull NoteView newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull NoteView oldItem, @NonNull NoteView newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority() &&
                    oldItem.getCategoryName().equals(newItem.getCategoryName()) &&
                    oldItem.getCategoryColor().equals(newItem.getCategoryColor());
        }
    };
    //   private List<Note> notes = new ArrayList<>();
    //private List<Category> categories = new ArrayList<>();
    private OnItemClickListener listener;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

        NoteView currentNote = getItem(position); // notes.get(position);

        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());
        holder.textViewPriority.setText(String.valueOf(currentNote.getPriority()));
        holder.textViewCategory.setText(currentNote.getCategoryName());
        holder.itemView.setBackgroundColor(Color.parseColor(currentNote.getCategoryColor()));

    }

    public NoteView getNoteAt(int position) {
        return getItem(position); //notes.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(NoteView note);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;
        private TextView textViewCategory;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);
            textViewCategory = itemView.findViewById(R.id.text_view_category);

            // Hier wird definiert, was ein OnClickListener für ein item ist. Da a priori nicht wie bei einem Button oder EditText klar ist, was ein item (hier: carditem)
            // alles enthält, kann der OnClickListener wohl nicht vordefiniert sein.
            // Auf das Click event lauschen muss aber die Aktivität, die den RecyclerView darstellt, also hier die MainActivity.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        // Der listener wäre null, wenn die darstellende Aktivität keinen OnClickListener implementiert hat.
                        listener.onItemClick(getItem(position));
                        // 'this.listener' geht hier nicht, aber NoteAdapter.this.listener:
                        // 'this' ist hier der NoteHolder; listener is das Feld aus dem umgebenden NoteAdapter Objekt.
                    }
                }
            });
        }
    }
}
